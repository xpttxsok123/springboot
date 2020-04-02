package com.xp.demo.thread;

/**
interrupt()方法其实只是改变了中断状态而已,不需要获取Thread实例的锁定,任何线程在任何时刻，都可以通过线程实例来调用其他线程的interrupt方法。

sleep、wait和join这些方法的内部会不断的检查中断状态的值，从而自己抛出InterruptEdException


抛InterruptedException的代表方法有：
 1. Java.lang.Object 类的 wait 方法
    执行wait方法的线程，会进入等待区等待被notify/notify All。在等待期间，线程不会活动。
 2. java.lang.Thread 类的 sleep 方法
 3. java.lang.Thread 类的 join 方法
    执行join方法的线程，会等待到指定的线程结束为止。


sleep方法与interrupt方法
    当在sleep中的线程被调用interrupt方法时，就会放弃暂停的状态，并抛出InterruptedException异常，这样一来，线程的控制权就交给了捕捉这个异常的catch块了。

wait方法和interrupt方法
 当线程调用wait方法后，线程在进入等待区时，会把锁定解除，对wait中的线程调用interrupt方法时，会先重新获取锁定，再抛出InterruptedException异常，获取锁定之前，无法抛出InterruptedException异常

 join方法和interrupt方法
 当线程以join方法等待其他线程结束时，一样可以使用interrupt方法取消。因为join方法不需要获取锁定，故而与sleep一样，会马上跳到catch程序块


 */

// TODO: 2020-04-02
public class InterruptDemo {
}
