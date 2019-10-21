package com.carlinx.shiro.service.impl;

import com.carlinx.shiro.base.service.impl.BaseServiceImpl;
import com.carlinx.shiro.entity.dbo.UserDBO;
import com.carlinx.shiro.mapper.UserMapper;
import com.carlinx.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserDBO> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDBO selectByUserName(String userName) {
        Example example = new Example(UserDBO.class);
        example.createCriteria().andEqualTo("userName",userName);
        UserDBO userDBO = userMapper.selectOneByExample(example);
        return userDBO;
    }

    @Override
    public UserDBO selectByPrimaryKey(Long userId) {
        UserDBO userDBO = userMapper.selectByPrimaryKey(userId);
        return userDBO;
    }
}
