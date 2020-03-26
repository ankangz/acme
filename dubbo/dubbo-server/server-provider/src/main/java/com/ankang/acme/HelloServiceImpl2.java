package com.ankang.acme;

public class HelloServiceImpl2 implements IHelloService {

    @Override
    public String sayHello(String msg) {
        return msg+"version:2";
    }
}
