package com.carlinx.shiro.base.service.impl;


import com.carlinx.shiro.base.service.BaseService;
import com.carlinx.shiro.base.tkmapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    protected BaseMapper<T> mapper;

    public BaseServiceImpl() {
    }

    public BaseMapper<T> getBaseMapper() {
        return this.mapper;
    }

    public T selectOne(T t) {
        return this.mapper.selectOne(t);
    }

    public List<T> select(T t) {
        return this.mapper.select(t);
    }

    public T selectByPrimaryKey(Object key) {
        return this.mapper.selectByPrimaryKey(key);
    }

    public List<T> selectAll() {
        return this.mapper.selectAll();
    }

    public List<T> selectField(String field, T t) {
        return this.mapper.selectField(field, t);
    }

    public List<T> selectByExample(Object example) {
        return this.mapper.selectByExample(example);
    }

    public Integer selectCountDistinct(String field, T t) {
        return this.mapper.selectCountDistinct(t, field);
    }

    public Integer selectCountDistinctByExample(String field, Object example) {
        return this.mapper.selectCountDistinctByExample(example, field);
    }

    public BigDecimal selectSum(String field, T t) {
        return this.mapper.selectSum(t, field);
    }

    public int selectCountByExample(Object example) {
        return this.mapper.selectCountByExample(example);
    }

    public List<T> selectByPrimaryKeys(Object... ids) {
        return this.mapper.selectByPrimaryKeys(ids);
    }

    public T selectFirstOne(T t) {
        return this.mapper.selectFirstOne(t);
    }

    public int insert(T t) {
        return this.mapper.insert(t);
    }

    public int insertList(List<T> ts) {
        return this.mapper.insertList(ts);
    }

    public int insertListUsePrimaryKey(List<T> ts) {
        return this.mapper.insertListUsePrimaryKey(ts);
    }

    public int insertSelective(T t) {
        return this.mapper.insertSelective(t);
    }

    public int insertUseGeneratedKeys(T t) {
        return this.mapper.insertUseGeneratedKeys(t);
    }

    public int updateByExample(T record, Object example) {
        return this.mapper.updateByExample(record, example);
    }

    public int updateByExampleSelective(T record, Object example) {
        return this.mapper.updateByExampleSelective(record, example);
    }

    public int updateByPrimaryKey(T record) {
        return this.mapper.updateByPrimaryKey(record);
    }

    public int updateByPrimaryKeySelective(T record) {
        return this.mapper.updateByPrimaryKeySelective(record);
    }

    public int delete(T record) {
        return this.mapper.delete(record);
    }

    public int deleteByPrimaryKey(Object... keys) {
        return this.mapper.deleteByPrimaryKeys(keys);
    }

    public int deleteByExample(Object example) {
        return this.mapper.deleteByExample(example);
    }
}
