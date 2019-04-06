package com.youxiong.netty2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * @ClassName ServerBootStarp
 * @Description TODO
 * @Author
 * @Date 2019/3/25 13:47
 *
 *
 * https://segmentfault.com/a/1190000007403873#articleHeader4
 *
 *
 **/
public class ServerBootStarp {

    public static void main(String[] args){
        System.out.println(SystemPropertyUtil.getInt("io.netty.eventLoopThreads", Runtime.getRuntime().availableProcessors() * 2));
    }


    /**
     * Reactor 单线程模型.
     *
     * @throws Exception
     */
    public void testSimpleThread() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        ServerBootstrap b = new ServerBootstrap();

        //置了服务器端的EventLoopGroup
        //ServerBootstrap 重写了 group 方法
        //当传入一个 group 时, 那么 bossGroup 和 workerGroup 就是同一个 NioEventLoopGroup 了
        // 并且这个 NioEventLoopGroup 只有一个线程, 这样就会导致 Netty 中的 acceptor 和后续的所有客户端连接的 IO 操作都是在一个线程中处理的.
        // 那么对应到 Reactor 的线程模型中, 我们这样设置 NioEventLoopGroup 时, 就相当于 Reactor 单线程模型.
        b.group(bossGroup).channel(NioServerSocketChannel.class);

    }


    /**
     * Reactor多线程模型
     * bossGroup 中只有一个线程, 而 workerGroup 中的线程是 CPU 核心数乘以2,
     * 因此对应的到 Reactor 线程模型中, 我们知道, 这样设置的 NioEventLoopGroup 其实就是 Reactor 多线程模型.
     *
     * 主从多线程模型确实是没说对哈，不过楼主自己已经修改过来了。常说Reactor线程模型，
     * 那什么是Reactor呢？可以这样理解，Reactor就是一个执行while (true) { selector.select(); ...}循环的线程，会源源不断的产生新的事件，称作反应堆很贴切。
     事件又分为连接事件、IO读和IO写事件，一般把连接事件单独放一线程里处理，即主Reactor（MainReactor），IO读和IO写事件放到另外的一组线程里处理，即从Reactor（SubReactor），从Reactor线程数量一般为2*(CPUs - 1)。
     所以在运行时，MainReactor只处理Accept事件，连接到来，马上按照策略转发给从Reactor之一，只处理连接，故开销非常小；
     每个SubReactor管理多个连接，负责这些连接的读和写，属于IO密集型线程，读到完整的消息就丢给业务线程池处理业务，处理完比后，响应消息一般放到队列里，SubReactor会去处理队列，然后将消息写回。
     *
     * @throws Exception
     */
    public void testMuiltThread() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();


        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);

    }


    /**
     *
     */
    public void testMasterSlaverMuiltThread() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(4);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class);
    }


}
