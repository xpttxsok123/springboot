package com.xp.demo.thread.Lock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class BooleanLock implements Lock {

    //initValue = true The lock has been got
    //initValue = false The lock has been released
    private Boolean initValue;

    //recored Blocked Threads
    private Collection<Thread> blockedThreadCollection = new ArrayList<>();

    private Thread current;

    public BooleanLock(Boolean initValue) {
        this.initValue = initValue;
    }

    @Override
    public synchronized void lock() throws InterruptedException {

        //锁已经背别人拿到进入wait
        //这里不能同if(), 因为wait会释放锁，如果用if， 当前线程把intValue释放锁后initValue为false，别的线程不会wait
        while (initValue) {
            blockedThreadCollection.add(Thread.currentThread());
            this.wait();
        }

        //获取锁成功
        initValue = true;
        current = Thread.currentThread();
        //从blockedThreadCollection删除
        blockedThreadCollection.remove(Thread.currentThread());
    }

    @Override
    public void lock(long time) throws InterruptedException, TimeoutException {

    }

    @Override
    public synchronized void unlock() {
        if(Thread.currentThread() == current){
            initValue = false;
            Optional.of("current thread release lock: " + Thread.currentThread().getName()).ifPresent(System.out::println);
            this.notifyAll();
        }
    }

    /**
     * getBlockLockThreads(): 这个方法是自读的，但是它不安全，因为返回出去的是Collection<Thread>的实列，可以对它修改，remove等
     *
     * @return
     */
    @Override
    public Collection<Thread> getBlockLockThreads() {
        // 返回不能背修改的Collection集合
        return Collections.unmodifiableCollection(blockedThreadCollection);
    }

    @Override
    public int getBlockedSize() {
        return blockedThreadCollection.size();
    }
}
