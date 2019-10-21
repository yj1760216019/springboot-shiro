package com.carlinx.shiro.config.realm;

import cn.hutool.core.collection.CollUtil;
import com.carlinx.shiro.base.token.JwtToken;
import com.carlinx.shiro.config.constant.JwtConstant;
import com.carlinx.shiro.config.constant.RedisConstant;
import com.carlinx.shiro.entity.dbo.*;
import com.carlinx.shiro.service.*;
import com.carlinx.shiro.utils.JwtUtil;
import com.carlinx.shiro.utils.SpringUtil;
import com.carlinx.shiro.utils.cache.RedisOperator;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;


public class UserRealm extends AuthorizingRealm {

    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleRelationService userRoleRelationService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionRelationService rolePermissionRelationService;



    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }




    /**
     * 需要检测用户权限时调用
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        redisOperator = SpringUtil.getBean(RedisOperator.class);
        userService = SpringUtil.getBean(UserService.class);
        roleService = SpringUtil.getBean(RoleService.class);
        permissionService = SpringUtil.getBean(PermissionService.class);
        userRoleRelationService = SpringUtil.getBean(UserRoleRelationService.class);
        rolePermissionRelationService = SpringUtil.getBean(RolePermissionRelationService.class);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String userId = JwtUtil.getClaim(principals.getPrimaryPrincipal().toString(), JwtConstant.PRIMART_KEY);
        //在此处判断缓存
        if(redisOperator.hasKey(RedisConstant.PREFIX_SHIRO_CACHE + userId)){
            //有缓存
             authorizationInfo = (SimpleAuthorizationInfo)redisOperator.get(RedisConstant.PREFIX_SHIRO_CACHE + userId);
            return authorizationInfo;
        }
        //查询用户角色关系
        List<UserRoleRelationDBO> userRoleRelationDBOS = userRoleRelationService.selectUserRoleRelationsByUserId(Long.parseLong(userId));
        if(CollUtil.isNotEmpty(userRoleRelationDBOS)){
            List<Long> roleIds = new ArrayList<>();
            for (UserRoleRelationDBO userRoleRelationDBO : userRoleRelationDBOS) {
                roleIds.add(userRoleRelationDBO.getRoleId());
            }
            //获取角色
            List<RoleDBO> roleDBOS = roleService.selectByPrimaryKeys(roleIds.toArray());
            List<String> roleIdentitys = new ArrayList<>();
            for (RoleDBO roleDBO : roleDBOS) {
                roleIdentitys.add(roleDBO.getRoleIdentity());
            }
            //添加用户角色
            authorizationInfo.addRoles(roleIdentitys);
            //查权限
            List<RolePermissionRelationDBO> rolePermissionRelationDBOS = rolePermissionRelationService.selectRolePermissionRelationsByRoleIds(roleIds);
            if(CollUtil.isNotEmpty(rolePermissionRelationDBOS)){
                List<Long> permissionIds = new ArrayList<>();
                for (RolePermissionRelationDBO rolePermissionRelationDBO : rolePermissionRelationDBOS) {
                    permissionIds.add(rolePermissionRelationDBO.getPermissionId());
                }
                List<PermissionDBO> permissionDBOS = permissionService.selectByPrimaryKeys(permissionIds.toArray());
                Set<String> permissions = new HashSet<>();
                for (PermissionDBO permissionDBO : permissionDBOS) {
                    permissions.add(permissionDBO.getPermissionIdentity());
                }
                authorizationInfo.addStringPermissions(permissions);
            }
        }
        redisOperator.set(RedisConstant.PREFIX_SHIRO_CACHE + userId,authorizationInfo);
        return authorizationInfo;
    }


    /**
     * 进行用户信息认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        redisOperator = SpringUtil.getBean(RedisOperator.class);
        userService = SpringUtil.getBean(UserService.class);
        String token = (String)authenticationToken.getCredentials();
        //解密获取用户主键
        String userId = JwtUtil.getClaim(token, JwtConstant.PRIMART_KEY);
        if(userId == null || userId == ""){
            throw new RuntimeException("解析token未获取到用户信息");
        }
        UserDBO userDBO = userService.selectByPrimaryKey(userId);
        if(userDBO == null){
            throw new RuntimeException("该账号不存在");
        }
        //比较token起到一方登录效果
        String accessToken = (String)redisOperator.get(RedisConstant.PREFIX_SHIRO_ACCESS_TOKEN + userId);
        if(accessToken != null && JwtUtil.verifyToken(accessToken) && token.equals(accessToken)){
            return new SimpleAuthenticationInfo(token,token,getName());
        }
        throw new RuntimeException("登录过期");
    }
}
