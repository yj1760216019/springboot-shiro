package com.carlinx.shiro.service;

import com.carlinx.shiro.entity.dbo.UserRoleRelationDBO;

import java.util.List;

public interface UserRoleRelationService {

    //根据用户id查询角色信息
    List<UserRoleRelationDBO> selectUserRoleRelationsByUserId(Long userId);

}
