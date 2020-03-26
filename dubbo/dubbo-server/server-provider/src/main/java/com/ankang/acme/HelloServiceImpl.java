package com.ankang.acme;

public class HelloServiceImpl implements IHelloService {

    @Override
    public String sayHello(String msg) {
        return msg+"version:1";
    }
}
