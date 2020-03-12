package com.ankang.client;

import com.ankang.client.zk.IServiceDiscover;
import com.ankang.client.zk.ServiceDiscoverImpl;
import com.ankang.client.zk.ZkConfig;
import com.ankang.server.IHelloService;

public class Client {
    public static void main(String[] args) {
        try {
            /*IHelloService helloService = (IHelloService) Naming.lookup("rmi://127.0.0.1/hello");
            String str = helloService.sayHello("ankang");
            System.out.println(str);*/

            IServiceDiscover serviceDiscover = new ServiceDiscoverImpl(ZkConfig.CONNECTION_STR);
            RpcClientProxy rpcClientProxy = new RpcClientProxy(serviceDiscover);
            IHelloService helloService = rpcClientProxy.clientProxy(IHelloService.class);
            System.out.println(helloService.sayHello("ankang"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
