package com.ankang.client;

import com.ankang.client.zk.IServiceDiscover;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteInvocationHandler implements InvocationHandler {
    private IServiceDiscover serviceDiscover;

    public RemoteInvocationHandler(IServiceDiscover serviceDiscover) {
        this.serviceDiscover = serviceDiscover;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);

        String serviceAddress = serviceDiscover.discover(rpcRequest.getClassName());
        TcpTransport tcpTransport = new TcpTransport(serviceAddress);
        return tcpTransport.send(rpcRequest);
    }
}
