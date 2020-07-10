package com.ankang.acme.user.dto;

import java.io.Serializable;

public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -3623698308723047329L;
    
    private String userName;
    private String password;

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
}
