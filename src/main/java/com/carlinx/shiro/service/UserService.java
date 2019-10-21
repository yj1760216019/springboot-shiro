package com.carlinx.shiro.service;


import com.carlinx.shiro.base.service.BaseService;
import com.carlinx.shiro.entity.dbo.UserDBO;

public interface UserService extends BaseService<UserDBO> {

    //根据用户名和密码查询用户
    UserDBO selectByUserName(String userName);

    UserDBO selectById(Long userId);

}
