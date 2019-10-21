package com.carlinx.shiro.service;

import com.carlinx.shiro.entity.dbo.PermissionDBO;

import java.util.List;
import java.util.Set;

public interface PermissionService {
    //根据角色集合查询用户权限
    List<PermissionDBO> selectPermissionsByIds(List<Long> permissionIds);
}
