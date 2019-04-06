/*
package com.xp.bio.bio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

*/
/**
 * @ClassName Client
 * @Description TODO
 * @Author
 * @Date 2019/3/23 14:02
 **//*

public class Client {


    final static int port = 8765;


    final static String ADDRESS = "127.0.0.1";


    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            //建立连接
            socket = new Socket(ADDRESS, port);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("客户端发送的数据");

            String s = in.readLine();
            System.out.println(s);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
*/
