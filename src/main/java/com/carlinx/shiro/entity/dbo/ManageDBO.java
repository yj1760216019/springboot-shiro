package com.carlinx.shiro.entity.dbo;

import java.io.Serializable;

/**
 * @Author yj
 * @Create 2019/11/12 18:27
 */

public class ManageDBO implements Serializable {

    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ManageDBO() {
    }

    public ManageDBO(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
