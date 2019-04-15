/*
package com.xp.bio.bio2;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

*/
/**
 * @ClassName ServerHandler
 * @Description TODO
 * @Author
 * @Date 2019/3/23 14:00
 **//*

public class ServerHandler extends Thread {


    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;

    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String body = null;
            while (true) {
                body = in.readLine();//readLine()方法将会阻塞，直到在一个由换行符或者回车符结尾的字符串被读取
                if (StringUtils.isEmpty(body)) {
                    break;
                }
                System.out.println(body);
                out.println("服务端发送的数据");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
*/
