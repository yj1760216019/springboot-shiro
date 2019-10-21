package com.carlinx.shiro.entity.dbo;

import com.carlinx.shiro.base.serializer.JsonSerializerLong;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Table(name = "role_permission_relation")
public class RolePermissionRelationDBO {

    //关系编号
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonSerialize(using = JsonSerializerLong.class)
    @OrderBy("desc")
    private Long id;
    //角色id
    private Long roleId;
    //权限id
    private Long permissionId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}
