package com.xp.bio.bio2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName Server
 * @Description TODO
 * @Author
 * @Date 2019/3/23 13:55
 **/
public class Server {
    final static int PORT = 8765;


    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            // 创建一个新的ServerSocket，用以监听指定端口上的连接请求
            serverSocket = new ServerSocket(PORT);
            System.out.println("server start");


            HandlerExecutorPool pool = new HandlerExecutorPool(50, 1000);

            while (true) {
                //服务器启动就会阻塞到这,main 函数阻塞在这里,对accept()方法的调用将被阻塞，直到一个连接建立
                Socket socket = serverSocket.accept();
                pool.execute(new ServerHandler(socket));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
