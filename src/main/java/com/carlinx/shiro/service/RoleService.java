package com.carlinx.shiro.service;

import com.carlinx.shiro.entity.dbo.RoleDBO;

import java.util.List;
import java.util.Set;

public interface RoleService {

    //根据用户id查询用户角色
    List<RoleDBO> selectRolesByIds(List<Long> roleIds);

}
