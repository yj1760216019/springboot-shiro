package com.carlinx.shiro.entity.dbo;

import com.carlinx.shiro.base.serializer.JsonSerializerLong;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;


@Table(name = "user")
public class UserDBO {
    //用户id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonSerialize(using = JsonSerializerLong.class)
    @OrderBy("desc")
    private Long userId;
    //用户名
    private String userName;
    //用户密码
    private String password;
    //盐值
    private String salt;
    //年龄
    private Integer age;
    //生日
    private Date birthday;
    //是否锁定
    //private Boolean lock;
    //用户状态
    private Integer status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
