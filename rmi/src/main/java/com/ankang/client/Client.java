package com.ankang.client;

import com.ankang.server.IHelloService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) {
        try {
            /*IHelloService helloService = (IHelloService) Naming.lookup("rmi://127.0.0.1/hello");
            String str = helloService.sayHello("ankang");
            System.out.println(str);*/
            RpcClientProxy rpcClientProxy = new RpcClientProxy();
            IHelloService helloService = rpcClientProxy.clientProxy(IHelloService.class, "localhost", 8888);
            System.out.println(helloService.sayHello("ankang"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
