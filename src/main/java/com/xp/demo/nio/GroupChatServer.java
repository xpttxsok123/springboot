package com.xp.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class GroupChatServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private static final int SERVER_POET = 8888;


    public GroupChatServer() throws Exception{
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.configureBlocking(Boolean.FALSE);
        this.serverSocketChannel.socket().bind(new InetSocketAddress(SERVER_POET));
        this.selector = Selector.open();
    }



    private void readMsg(){

        try {

            while (true){
                SocketChannel socketChannel = serverSocketChannel.accept();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int read = socketChannel.read(byteBuffer);




            }



        } catch (IOException e) {
            e.printStackTrace();
        }


    }





}
