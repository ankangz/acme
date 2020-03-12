package com.ankang.server;

import com.ankang.server.anno.RpcAnnotion;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@RpcAnnotion(IHelloService.class)
public class HelloServiceImpl implements IHelloService{

    public HelloServiceImpl()  {
        super();
    }

    public String sayHello(String name) {
        return "Hello :"+name;
    }
}
