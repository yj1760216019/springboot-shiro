package com.carlinx.shiro.service;

import com.carlinx.shiro.entity.dbo.PermissionDBO;
import com.carlinx.shiro.entity.dbo.RolePermissionRelationDBO;

import java.util.List;

public interface RolePermissionRelationService {

    //根据角色id集合查询权限集合
    List<RolePermissionRelationDBO> selectRolePermissionRelationsByRoleIds(List<Long> roleIds);
}
