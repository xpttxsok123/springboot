package com.youxiong.bio.bio1;

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
            serverSocket = new ServerSocket(PORT);
            System.out.println("server start");


            //服务器启动就会阻塞到这,main 函数阻塞在这里
            Socket socket = serverSocket.accept();

            new Thread(new ServerHandler(socket)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
