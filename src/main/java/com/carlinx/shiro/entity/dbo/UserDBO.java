package com.carlinx.shiro.entity.dbo;

import javax.persistence.Table;
import java.util.Date;


@Table(name = "user")
public class UserDBO {
    //用户id
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
    private Boolean lock;
    //用户状态
    private Integer status;

    public UserDBO() {
    }

    public UserDBO(Long userId, String userName, String password, String salt, Integer age, Date birthday, Boolean lock, Integer status) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.salt = salt;
        this.age = age;
        this.birthday = birthday;
        this.lock = lock;
        this.status = status;
    }

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

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
