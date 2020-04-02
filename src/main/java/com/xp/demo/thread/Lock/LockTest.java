package com.xp.demo.thread.Lock;

import java.util.Arrays;
import java.util.Optional;

public class LockTest {
    public static void main(String[] args) {
        BooleanLock booleanLock = new BooleanLock(false);
        Arrays.asList("T1", "T2", "T3", "T4").stream().forEach(t -> {
            new Thread(() -> {
                try {
                    //抢锁
                    booleanLock.lock();
                    Optional.of(Thread.currentThread().getName() + " get the lock monitor").ifPresent(System.out::println);

                    //模拟业务逻辑
                    work();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //确保释放锁
                    booleanLock.unlock();
                }
            }, t).start();
        });


        booleanLock.unlock();
        //在这里释放锁会有问题

    }


    //模拟业务逻辑
    public static final void work() throws InterruptedException {
        Optional
                .of(Thread.currentThread().getName() + " is working...")
                .ifPresent(System.out::println);

        Thread.sleep(5_000);
    }

}
