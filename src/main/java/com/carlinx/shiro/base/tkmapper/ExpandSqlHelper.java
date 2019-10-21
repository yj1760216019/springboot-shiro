package com.carlinx.shiro.base.tkmapper;

import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Iterator;
import java.util.Set;

public class ExpandSqlHelper extends SqlHelper {

    public ExpandSqlHelper() {
    }

    public static String wherePKColumnsIn(Class<?> entityClass) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        Set<EntityColumn> columnList = EntityHelper.getPKColumns(entityClass);
        sql.append(" <if test=\"ids != null \">");
        sql.append(" AND " + ((EntityColumn)columnList.iterator().next()).getColumn());
        sql.append(" IN ");
        sql.append(" <foreach item=\"item\" collection=\"ids\" separator=\",\" open=\"(\" close=\")\" index=\"\">  ");
        sql.append(" #{item} ");
        sql.append("</foreach>");
        sql.append("</if>");
        sql.append("</where>");
        return sql.toString();
    }

    public static String exampleWhereClause(String paramsName) {
        return "<where>\n ${@tk.mybatis.mapper.util.OGNL@andNotLogicDelete(" + paramsName + ")} <trim prefix=\"(\" prefixOverrides=\"and |or \" suffix=\")\">\n  <foreach collection=\"" + paramsName + ".oredCriteria\" item=\"criteria\">\n    <if test=\"criteria.valid\">\n      ${@tk.mybatis.mapper.util.OGNL@andOr(criteria)}      <trim prefix=\"(\" prefixOverrides=\"and |or \" suffix=\")\">\n        <foreach collection=\"criteria.criteria\" item=\"criterion\">\n          <choose>\n            <when test=\"criterion.noValue\">\n              ${@tk.mybatis.mapper.util.OGNL@andOr(criterion)} ${criterion.condition}\n            </when>\n            <when test=\"criterion.singleValue\">\n              ${@tk.mybatis.mapper.util.OGNL@andOr(criterion)} ${criterion.condition} #{criterion.value}\n            </when>\n            <when test=\"criterion.betweenValue\">\n              ${@tk.mybatis.mapper.util.OGNL@andOr(criterion)} ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n            </when>\n            <when test=\"criterion.listValue\">\n              ${@tk.mybatis.mapper.util.OGNL@andOr(criterion)} ${criterion.condition}\n              <foreach close=\")\" collection=\"criterion.value\" item=\"listItem\" open=\"(\" separator=\",\">\n                #{listItem}\n              </foreach>\n            </when>\n          </choose>\n        </foreach>\n      </trim>\n    </if>\n  </foreach>\n </trim>\n</where>";
    }

    public static String exampleForUpdate(String paramsName) {
        StringBuilder sql = new StringBuilder();
        sql.append("<if test=\"@tk.mybatis.mapper.util.OGNL@hasForUpdate(" + paramsName + ")\">");
        sql.append("FOR UPDATE");
        sql.append("</if>");
        return sql.toString();
    }

    public static String whereAllIfColumns(Class<?> entityClass, boolean empty, String entityName) {
        StringBuilder sql = new StringBuilder();
        sql.append("<where>");
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        Iterator var5 = columnList.iterator();

        while(var5.hasNext()) {
            EntityColumn column = (EntityColumn)var5.next();
            sql.append(getIfNotNull(entityName, column, " AND " + column.getColumnEqualsHolder(entityName), empty));
        }

        sql.append("</where>");
        return sql.toString();
    }

}
