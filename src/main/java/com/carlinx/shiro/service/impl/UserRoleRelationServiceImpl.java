package com.carlinx.shiro.service.impl;


import com.carlinx.shiro.base.service.impl.BaseServiceImpl;
import com.carlinx.shiro.entity.dbo.UserRoleRelationDBO;
import com.carlinx.shiro.mapper.UserRoleRelationMapper;
import com.carlinx.shiro.service.UserRoleRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserRoleRelationServiceImpl extends BaseServiceImpl<UserRoleRelationDBO> implements UserRoleRelationService {

    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;

    @Override
    public List<UserRoleRelationDBO> selectUserRoleRelationsByUserId(Long userId) {
        Example example = new Example(UserRoleRelationDBO.class);
        example.createCriteria().andEqualTo("userId",userId);
        List<UserRoleRelationDBO> userRoleRelationDBOS = userRoleRelationMapper.selectByExample(example);
        return userRoleRelationDBOS;
    }
}
