package com.ankang.acme;

public class TestMock implements IHelloService {
    @Override
    public String sayHello(String msg) {
        return "繁忙";
    }
}
