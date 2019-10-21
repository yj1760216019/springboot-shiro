package com.carlinx.shiro.controller;


import cn.hutool.core.util.StrUtil;
import com.carlinx.shiro.base.result.JsonResult;
import com.carlinx.shiro.config.constant.JwtConstant;
import com.carlinx.shiro.config.constant.RedisConstant;
import com.carlinx.shiro.entity.dbo.UserDBO;
import com.carlinx.shiro.service.UserService;
import com.carlinx.shiro.utils.JwtUtil;
import com.carlinx.shiro.utils.MD5Util;
import com.carlinx.shiro.utils.cache.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
@Api("用户认证管理")
public class UserApiController {
    @Value("${access-token-expire-time}")
    private String accessTokenExpireTime; //= "1200";
    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private UserService userService;


    @PutMapping("/login/{userName}/{password}")
    @ApiOperation("登录")
    public JsonResult login(@ApiParam("用户名") @PathVariable(value = "userName") String userName,
                            @ApiParam("密码")@PathVariable(value = "password") String password){
        if(StrUtil.isEmpty(userName) || StrUtil.isEmpty(password)){
            return JsonResult.fail("账号或密码为空");
        }
        //根据用户名从数据库中查账号信息
        UserDBO userDBO = userService.selectByUserName(userName);
        if(userDBO == null){
            return JsonResult.fail("账号或密码错误");
        }
        //比对密码
        if(!MD5Util.encode(password,userDBO.getSalt()).equals(userDBO.getPassword())){
            return JsonResult.fail("账号或密码错误");
        }
        String userId = String.valueOf(userDBO.getUserId());
        //清除可能存在的shiro权限信息缓存
        if (redisOperator.hasKey(RedisConstant.PREFIX_SHIRO_CACHE + userId)) {
            redisOperator.delete(RedisConstant.PREFIX_SHIRO_CACHE + userId);
        }
        //生成token
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        String accessToken = JwtUtil.signToken(userId, currentTimeMillis);
        //token加入缓存
        redisOperator.set(RedisConstant.PREFIX_SHIRO_ACCESS_TOKEN + userId,accessToken,Long.parseLong(accessTokenExpireTime));
        return JsonResult.success("登陆成功",accessToken);
    }



    @PutMapping("/logout")
    @ApiOperation("登出")
    public JsonResult logout(HttpServletRequest request, HttpServletResponse response){
        try {
            String accessToken = null;
            accessToken = request.getHeader("Authorization");
            if(StrUtil.isEmpty(accessToken)){
                return JsonResult.fail("未获取到token");
            }
            if(!JwtUtil.verifyToken(accessToken)){
                return JsonResult.fail("token失效或不正确");
            }
            String userId = JwtUtil.getClaim(accessToken, JwtConstant.PRIMART_KEY);
            if(StrUtil.isEmpty(userId)){
                return JsonResult.fail("token失效或不正确");
            }
            // 清除shiro权限信息缓存
            if (redisOperator.hasKey(RedisConstant.PREFIX_SHIRO_CACHE + userId)) {
                redisOperator.delete(RedisConstant.PREFIX_SHIRO_CACHE + userId);
            }
            //清除缓存
            redisOperator.delete(RedisConstant.PREFIX_SHIRO_ACCESS_TOKEN + userId);
            return JsonResult.success("退出成功",true);
        }catch (Exception e){
            throw new RuntimeException("退出登陆失败" + e.getMessage());
        }
    }



    @PutMapping("/unLogin")
    @ApiOperation("未登录")
    public JsonResult unLogin(){
        return JsonResult.fail("请登录","10005");
    }





}
