package com.ankang.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class RpcClientProxy {

    public <T> T clientProxy(final Class<T> interfaceCls,
                             final String host,final int port){

        return (T)Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class[]{interfaceCls},new RemoteInvocationHandler(host,port));

    }

}
