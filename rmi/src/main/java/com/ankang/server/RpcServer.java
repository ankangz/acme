package com.ankang.server;

import com.ankang.server.anno.RpcAnnotion;
import com.ankang.server.zk.IRegisterCenter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServer {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private IRegisterCenter registerCenter;//注册中心
    private String serviceAddress;//服务发布地址

    //存放服务名称和服务对象之间的关系
    private Map<String,Object> handlerMap = new HashMap();

    public RpcServer(IRegisterCenter registerCenter, String serviceAddress) {
        this.registerCenter = registerCenter;
        this.serviceAddress = serviceAddress;
    }

    /**
     * 绑定读物对象和服务名称
     * @param services
     */
    public void bind(Object... services){
        for (Object service:services) {
            RpcAnnotion annotation = service.getClass().getAnnotation(RpcAnnotion.class);
            String serviceName = annotation.value().getName();
            handlerMap.put(serviceName,service);//绑定服务名称和服务

        }

    }

    public void publisher(){
        ServerSocket serverSocket = null;

        try {
            String[] sas = serviceAddress.split(":");
            serverSocket = new ServerSocket(Integer.parseInt(sas[1]));

            for (String interfaceName: handlerMap.keySet()
                 ) {
                registerCenter.register(interfaceName,serviceAddress);
                System.out.println("服务注册成功->"+interfaceName+"->"+serviceAddress);
            }
            while (true){
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessorHandler(socket,handlerMap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(serverSocket != null){
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
