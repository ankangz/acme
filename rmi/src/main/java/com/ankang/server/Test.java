package com.ankang.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Test {

    public static void main(String[] args) {
        try {
            IHelloService helloService  = new HelloServiceImpl();
            //helloService.sayHello("ankang");
            RpcServer rpcServer = new RpcServer();
            rpcServer.publisher(helloService,8888);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
