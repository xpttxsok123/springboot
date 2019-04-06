package com.youxiong.netty;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @ClassName NioSelectorClient
 * @Description TODO
 * @Author
 * @Date 2019/3/25 12:53
 **/
public class NioSelectorClient {
    public static void main(String[] args) throws IOException {

    }


    @Test
    public void test() throws IOException {
      test1();
      test2();
    }

    public void test1() throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress("127.0.0.1", 8000));
    }

    public void test2() throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress("127.0.0.1", 8000));
    }

}
