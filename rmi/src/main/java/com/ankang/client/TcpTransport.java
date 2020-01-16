package com.ankang.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TcpTransport {
    private String host;
    private int port;

    public TcpTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private Socket newSocket(){
        System.out.println("建立一个新的连接");
        Socket socket = null;
        try {
            socket = new Socket(host,port);

        } catch (IOException e) {
            System.out.println("建立连接失败");
            e.printStackTrace();
        }
        return socket;
    }


    public Object send(RpcRequest rpcRequest){

        Socket socket = null;
        Object result = null;
        try {
            socket = this.newSocket();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            result = objectInputStream.readObject();

            objectInputStream.close();
            objectOutputStream.close();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("调用一场");
        } finally {
            try {
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
