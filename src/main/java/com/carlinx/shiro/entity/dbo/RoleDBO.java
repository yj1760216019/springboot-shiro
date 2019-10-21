package com.carlinx.shiro.entity.dbo;

import com.carlinx.shiro.base.serializer.JsonSerializerLong;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Table(name = "role")
public class RoleDBO {
    //角色id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonSerialize(using = JsonSerializerLong.class)
    @OrderBy("desc")
    private Long roleId;
    //角色名称
    private String roleName;
    //角色标识
    private String roleIdentity;
    //角色描述
    private String roleDescription;
    //角色状态
    private Integer status;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRoleIdentity() {
        return roleIdentity;
    }

    public void setRoleIdentity(String roleIdentity) {
        this.roleIdentity = roleIdentity;
    }
}
