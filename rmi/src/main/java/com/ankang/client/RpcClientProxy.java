package com.ankang.client;

import com.ankang.client.zk.IServiceDiscover;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class RpcClientProxy {
    private IServiceDiscover serviceDiscover;

    public RpcClientProxy(IServiceDiscover serviceDiscover) {
        this.serviceDiscover = serviceDiscover;
    }

    public <T> T clientProxy(final Class<T> interfaceCls){

        return (T)Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class[]{interfaceCls},new RemoteInvocationHandler(serviceDiscover));

    }

}
