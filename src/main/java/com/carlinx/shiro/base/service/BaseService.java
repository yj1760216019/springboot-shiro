package com.carlinx.shiro.base.service;


import com.carlinx.shiro.base.tkmapper.BaseMapper;
import java.math.BigDecimal;
import java.util.List;

public interface BaseService<T> {

    BaseMapper<T> getBaseMapper();

    T selectOne(T var1);

    List<T> select(T var1);

    T selectByPrimaryKey(Object var1);

    List<T> selectAll();

    List<T> selectField(String var1, T var2);

    List<T> selectByExample(Object var1);

    Integer selectCountDistinct(String var1, T var2);

    Integer selectCountDistinctByExample(String var1, Object var2);

    BigDecimal selectSum(String var1, T var2);

    int selectCountByExample(Object var1);

    List<T> selectByPrimaryKeys(Object... var1);

    T selectFirstOne(T var1);

    int insert(T var1);

    int insertList(List<T> var1);

    int insertListUsePrimaryKey(List<T> var1);

    int insertSelective(T var1);

    int insertUseGeneratedKeys(T var1);

    int updateByExample(T var1, Object var2);

    int updateByExampleSelective(T var1, Object var2);

    int updateByPrimaryKey(T var1);

    int updateByPrimaryKeySelective(T var1);

    int delete(T var1);

    int deleteByPrimaryKey(Object... var1);

    int deleteByExample(Object var1);

}
