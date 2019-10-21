package com.carlinx.shiro.service.impl;

import com.carlinx.shiro.base.service.impl.BaseServiceImpl;
import com.carlinx.shiro.entity.dbo.PermissionDBO;
import com.carlinx.shiro.mapper.PermissionMapper;
import com.carlinx.shiro.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Set;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionDBO> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;



    @Override
    public List<PermissionDBO> selectPermissionsByIds(List<Long> permissionIds) {
        List<PermissionDBO> permissionDBOS = permissionMapper.selectByPrimaryKeys(permissionIds);
        return permissionDBOS;
    }
}
