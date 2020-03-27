package com.ankang.acme;

public class MysqlDriver implements DataBaseDriver {
    @Override
    public String connection(String host) {
        return "msyqlDriver";
    }
}
