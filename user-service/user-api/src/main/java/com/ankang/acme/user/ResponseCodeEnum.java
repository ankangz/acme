package com.ankang.acme.user;

public enum  ResponseCodeEnum {
    SYSTEM_BUSY("001099","系统繁忙，请稍后重试!"),
    SYS_PARAM_NOT_RIGHT("a","s");
    
    private String code;
    private String msg;

    ResponseCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
