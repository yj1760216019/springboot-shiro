package com.carlinx.shiro.base.tkmapper;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Iterator;
import java.util.Set;

public class ExpandProvider extends MapperTemplate {

    public ExpandProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String selectFirstOne(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        this.setResultType(ms, entityClass);
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(SqlHelper.selectAllColumns(entityClass));
        sqlBuilder.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sqlBuilder.append(SqlHelper.whereAllIfColumns(entityClass, this.isNotEmpty()));
        sqlBuilder.append(SqlHelper.orderByDefault(entityClass));
        sqlBuilder.append(" limit 1");
        return sqlBuilder.toString();
    }

    public String selectFirstOneByExample(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        this.setResultType(ms, entityClass);
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append(SqlHelper.selectAllColumns(entityClass));
        sqlBuilder.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sqlBuilder.append(SqlHelper.exampleWhereClause());
        sqlBuilder.append(SqlHelper.exampleForUpdate());
        sqlBuilder.append(" limit 1");
        return sqlBuilder.toString();
    }

    public String selectByPrimaryKeys(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        this.setResultType(ms, entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.selectAllColumns(entityClass));
        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sql.append(ExpandSqlHelper.wherePKColumnsIn(entityClass));
        return sql.toString();
    }

    public String selectFieldByPrimaryKeys(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append("select ${field} ");
        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sql.append(ExpandSqlHelper.wherePKColumnsIn(entityClass));
        return sql.toString();
    }

    public String selectField(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append("select ${field} ");
        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sql.append(ExpandSqlHelper.whereAllIfColumns(entityClass, this.isNotEmpty(), "obj"));
        return sql.toString();
    }

    public String selectFieldDistinct(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct ${field} ");
        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sql.append(ExpandSqlHelper.whereAllIfColumns(entityClass, this.isNotEmpty(), "obj"));
        return sql.toString();
    }

    public String deleteByPrimaryKeys(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.deleteFromTable(entityClass, this.tableName(entityClass)));
        sql.append(ExpandSqlHelper.wherePKColumnsIn(entityClass));
        return sql.toString();
    }

    public String selectSum(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append("SUM(${field}) ");
        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sql.append(ExpandSqlHelper.whereAllIfColumns(entityClass, this.isNotEmpty(), "obj"));
        return sql.toString();
    }

    public String selectCountDistinct(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append("count(distinct ${field})");
        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sql.append(ExpandSqlHelper.whereAllIfColumns(entityClass, this.isNotEmpty(), "obj"));
        return sql.toString();
    }

    public String selectCountDistinctByExample(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(" count( distinct ${field})");
        sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
        sql.append(ExpandSqlHelper.exampleWhereClause("example"));
        sql.append(ExpandSqlHelper.exampleForUpdate("example"));
        return sql.toString();
    }

    public String insertListUsePrimaryKey(MappedStatement ms) {
        Class<?> entityClass = this.getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.insertIntoTable(entityClass, this.tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, false, false, false));
        sql.append(" VALUES ");
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        Iterator var5 = columnList.iterator();

        while(var5.hasNext()) {
            EntityColumn column = (EntityColumn)var5.next();
            if (column.isInsertable()) {
                sql.append(column.getColumnHolder("record") + ",");
            }
        }

        sql.append("</trim>");
        sql.append("</foreach>");
        return sql.toString();
    }
}
