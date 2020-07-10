package com.ankang.acme.user;

public enum  ResponseCodeEnum {
    SUCCESS("000000","登录成功!"),
    USERORPASSWOR_ERROR("001001","用户名或密码不存在!"),
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
