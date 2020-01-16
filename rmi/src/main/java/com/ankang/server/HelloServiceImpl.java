package com.ankang.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServiceImpl implements IHelloService{

    public HelloServiceImpl()  {
        super();
    }

    public String sayHello(String name) {
        return "Hello :"+name;
    }
}
