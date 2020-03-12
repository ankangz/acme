package com.ankang.server;

import com.ankang.server.zk.IRegisterCenter;
import com.ankang.server.zk.RegisterCenterImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Test {

    public static void main(String[] args) {
        try {
            IHelloService helloService  = new HelloServiceImpl();
            //helloService.sayHello("ankang");
            IRegisterCenter registerCenter = new RegisterCenterImpl();


            RpcServer rpcServer = new RpcServer(registerCenter,"127.0.0.1:8888");
            rpcServer.bind(helloService);
            rpcServer.publisher();
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
