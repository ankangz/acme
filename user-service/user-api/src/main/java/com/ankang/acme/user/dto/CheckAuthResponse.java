package com.ankang.acme.user.dto;

import java.io.Serializable;

public class CheckAuthResponse implements Serializable {
    
    
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "CheckAuthResponse{" +
                "uid='" + uid + '\'' +
                '}';
    }
}
