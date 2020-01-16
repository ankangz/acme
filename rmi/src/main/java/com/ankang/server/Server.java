package com.ankang.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    public static void main(String[] args) {
        try {
            IHelloService helloService  = new HelloServiceImpl();
            LocateRegistry.createRegistry(1099);
            /*Naming.rebind("rmi://127.0.0.1/hello",helloService);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
