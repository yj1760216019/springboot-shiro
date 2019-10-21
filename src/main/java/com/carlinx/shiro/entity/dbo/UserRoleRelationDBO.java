package com.carlinx.shiro.entity.dbo;

import com.carlinx.shiro.base.serializer.JsonSerializerLong;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Table(name = "user_role_relation")
public class UserRoleRelationDBO {
    //关系编号
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonSerialize(using = JsonSerializerLong.class)
    @OrderBy("desc")
    private Long id;
    //用户id
    private Long userId;
    //角色id
    private Long roleId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
