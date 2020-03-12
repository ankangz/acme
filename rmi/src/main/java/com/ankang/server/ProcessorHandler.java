package com.ankang.server;

import com.ankang.client.RpcRequest;
import com.ankang.server.zk.IRegisterCenter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ProcessorHandler implements Runnable{
    private Socket socket;

    //存放服务名称和服务对象之间的关系
    private Map<String,Object> handlerMap;

    public ProcessorHandler(Socket socket, Map<String,Object> handlerMap) {
        this.socket = socket;
        this.handlerMap = handlerMap;
    }

    public void run() {

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();

            Object result = this.invoke(rpcRequest);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
            objectOutputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public Object invoke(RpcRequest rpcRequest) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Object[] args = rpcRequest.getParameters();
        Class[] types = new Class[args.length];

        for (int i=0;i<args.length;i++) {
            types[i] = args[i].getClass();
        }
        Object service = handlerMap.get(rpcRequest.getClassName());
        Method method = service.getClass().getMethod(rpcRequest.getMethodName(), types);
        return method.invoke(service,args);
        
    }
}
