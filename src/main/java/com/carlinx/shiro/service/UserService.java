package com.carlinx.shiro.service;


import com.carlinx.shiro.entity.dbo.UserDBO;

public interface UserService {

    //根据用户名和密码查询用户
    UserDBO selectByUserName(String userName);
    //根据主键查用户信息
    UserDBO selectByPrimaryKey(Long userId);

}
