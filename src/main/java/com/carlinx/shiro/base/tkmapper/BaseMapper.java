package com.carlinx.shiro.base.tkmapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.math.BigDecimal;
import java.util.List;

public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

    @SelectProvider(
            type = ExpandProvider.class,
            method = "dynamicSQL"
    )
    List<T> selectByPrimaryKeys(@Param("ids") Object... var1);

    @SelectProvider(
            type = ExpandProvider.class,
            method = "dynamicSQL"
    )
    <S> List<S> selectFieldByPrimaryKeys(@Param("field") String var1, @Param("ids") Object... var2);

    @SelectProvider(
            type = ExpandProvider.class,
            method = "dynamicSQL"
    )
    <S> List<S> selectField(@Param("field") String var1, @Param("obj") T var2);

    @SelectProvider(
            type = ExpandProvider.class,
            method = "dynamicSQL"
    )
    <S> List<S> selectFieldDistinct(@Param("field") String var1, @Param("obj") T var2);

    @SelectProvider(
            type = ExpandProvider.class,
            method = "dynamicSQL"
    )
    T selectFirstOne(T var1);

    @SelectProvider(
            type = ExpandProvider.class,
            method = "dynamicSQL"
    )
    T selectFirstOneByExample(Object var1);

    @DeleteProvider(
            type = ExpandProvider.class,
            method = "dynamicSQL"
    )
    int deleteByPrimaryKeys(@Param("ids") Object... var1);

    @SelectProvider(
            type = ExpandProvider.class,
            method = "dynamicSQL"
    )
    BigDecimal selectSum(@Param("obj") T var1, @Param("field") String var2);

    @SelectProvider(
            type = ExpandProvider.class,
            method = "dynamicSQL"
    )
    int selectCountDistinct(@Param("obj") T var1, @Param("field") String var2);

    @SelectProvider(
            type = ExpandProvider.class,
            method = "dynamicSQL"
    )
    int selectCountDistinctByExample(@Param("example") Object var1, @Param("field") String var2);

    @InsertProvider(
            type = ExpandProvider.class,
            method = "dynamicSQL"
    )
    int insertListUsePrimaryKey(List<T> var1);

}
