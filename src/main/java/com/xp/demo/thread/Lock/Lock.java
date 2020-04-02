package com.xp.demo.thread.Lock;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

public interface Lock {

    static class TimeOutException extends Exception {
        public TimeOutException(String message) {
            super(message);
        }
    }

    //允许线程被打断
    void lock() throws InterruptedException;

    //在指定的时间内允许中断，比较耗时的操作都允许抛出中断异常如：wait(); sleep(); join();   syschnized不能背被中断
    // 如果在指定的时间内没有获取到锁，就抛出timeOutException
    void lock(long time) throws InterruptedException, TimeoutException;

    void unlock();

    // 获取被阻塞的线程
    Collection<Thread> getBlockLockThreads();

    // 获取被阻塞线程的数量
    int getBlockedSize();

}
