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

/**
 * @Author yj
 * @Create 2019/11/12 18:14
 */

public class ManageRealm extends AuthorizingRealm {
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



    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }

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
        UserDBO userDBO = new UserDBO();
        userDBO.setUserName("yj");
        userDBO.setPassword("d53732ccbfa4af28b3c0e7665cf69009");
        userDBO.setBirthday(new Date());
        userDBO.setUserId(101L);
        userDBO.setSalt("asdf");
        userDBO.setAge(1);
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
