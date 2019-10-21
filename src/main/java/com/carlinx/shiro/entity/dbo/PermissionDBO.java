package com.carlinx.shiro.entity.dbo;


import com.carlinx.shiro.base.serializer.JsonSerializerLong;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Table(name = "permission")
public class PermissionDBO {
    //权限id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonSerialize(using = JsonSerializerLong.class)
    @OrderBy("desc")
    private Long permissionId;
    //权限名称
    private String permissionName;
    //权限标识
    private String permissionIdentity;
    //权限描述
    private String permissionDescription;
    //状态
    private Integer status;

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionIdentity() {
        return permissionIdentity;
    }

    public void setPermissionIdentity(String permissionIdentity) {
        this.permissionIdentity = permissionIdentity;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
