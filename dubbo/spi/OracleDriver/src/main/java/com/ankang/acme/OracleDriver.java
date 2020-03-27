package com.ankang.acme;

public class OracleDriver implements DataBaseDriver {
    @Override
    public String connection(String host) {
        return "OracleDriver";
    }
}
