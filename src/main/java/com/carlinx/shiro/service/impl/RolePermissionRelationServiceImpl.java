package com.carlinx.shiro.service.impl;

import com.carlinx.shiro.base.service.impl.BaseServiceImpl;
import com.carlinx.shiro.entity.dbo.RolePermissionRelationDBO;
import com.carlinx.shiro.mapper.RolePermissionRelationMapper;
import com.carlinx.shiro.service.RolePermissionRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
public class RolePermissionRelationServiceImpl extends BaseServiceImpl<RolePermissionRelationDBO> implements RolePermissionRelationService {
    @Autowired
    private RolePermissionRelationMapper rolePermissionRelationMapper;



    @Override
    public List<RolePermissionRelationDBO> selectRolePermissionRelationsByRoleIds(List<Long> roleIds) {
        Example example = new Example(RolePermissionRelationDBO.class);
        example.createCriteria().andIn("roleId",roleIds);
        List<RolePermissionRelationDBO> rolePermissionRelationDBOS = rolePermissionRelationMapper.selectByExample(example);
        return rolePermissionRelationDBOS;
    }
}
