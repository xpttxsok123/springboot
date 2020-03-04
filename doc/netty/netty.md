##1.Netty 简介
```
Netty is a NIO client server framework which enables quick and easy development of network 
applications such as protocol servers and clients. It greatly simplifies and streamlines 
network programming such as TCP and UDP socket server.

Netty是一个NIO客户端服务器框架，可以快速轻松地开发网络协议服务器和客户端等应用程序。 它极大地简化和精简了
网络编程，如TCP和UDP套接字服务器。
protocol：协议
development：发展，开发
simplifies： 简化
streamline： 精简



'Quick and easy' doesn't mean that a resulting application will suffer from a maintainability 
or a performance issue. Netty has been designed carefully with the experiences earned from the 
implementation of a lot of protocols such as FTP, SMTP, HTTP, and various binary and text-based 
legacy protocols. As a result, Netty has succeeded to find a way to achieve ease of development, 
performance, stability, and flexibility without a compromise.

“快速简便”并不意味着最终的应用程序会受到可维护性的影响或性能问题。Netty经过精心设计，具有丰富的协议，如FTP，
SMTP，HTTP以及各种二进制和基于文本的遗留协议所获得的最佳实践,Netty成功地找到了一种实现易于开发的方法，性能、
稳定性和灵活性没有妥协。

maintainability：可维护性 （n）
performance： 性能，表演 （Noun）
implementation：履行 （Noun）
various：各个 (Adjective)
legacy：遗产 (Noun)
flexibility：灵活性(Noun)
stability：稳定性(Noun)
compromise：妥协(Noun)

```

##2.Netty 架构
<img src="https://netty.io/images/components.png"  div align=right />


##3.Netty Features
```
1.Unified API for various transport types - blocking and non-blocking socket
适用于各种传输类型的统一API
2.Based on a flexible and extensible event model which allows clear separation of concerns
基于灵活且可扩展的事件模型，可以清晰地分离关注点
3.Highly customizable thread model - single thread, one or more thread pools such as SEDA
4.True connectionless datagram socket support (since 3.1)

unified ：统一的（Adjective）
flexible：灵活的 （Adjective）
extensible：扩展的 （Adjective）
separation：分割	(Noun)
customizable：可定制的（Adjective）
Datagram Sockets 数据报套接字

```




##4.Performance
```
Better throughput, lower latency
Less resource consumption
Minimized unnecessary memory copy

更高的吞吐量，更低的延迟
减少资源消耗
最小化不必要的内存复制

consumption: 消费(Noun)
```

##5.Community
```
Release early, release often
The author has been writing similar frameworks since 2003 and he still finds your feed back precious!

早发布，经常发布
自2003年以来，作者一直在编写类似的框架，他仍然发现你的反馈很珍贵！
```
