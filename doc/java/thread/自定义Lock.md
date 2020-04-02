#自定义Lock

``interface``
 
```java
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

```

``implement``
```java
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

        //从blockedThreadCollection删除
        blockedThreadCollection.remove(Thread.currentThread());
    }

    @Override
    public void lock(long time) throws InterruptedException, TimeoutException {

    }

    @Override
    public synchronized void unlock() {
        initValue = false;
        Optional.of("current thread release lock: " + Thread.currentThread().getName()).ifPresent(System.out::println);
        this.notifyAll();
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

```

``Test``
```java
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
                    System.out.println(booleanLock.getBlockedSize());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //确保释放锁
                    booleanLock.unlock();
                }
            }, t).start();
        });
    }


    //模拟业务逻辑
    public static final void work() throws InterruptedException {
        Optional
                .of(Thread.currentThread().getName() + " is working...")
                .ifPresent(System.out::println);

        Thread.sleep(5_000);
    }

}

```

``运行结果``
```text
Connected to the target VM, address: '127.0.0.1:60952', transport: 'socket'
T1 get the lock monitor
T1 is working...
current thread release lock: T1
T4 get the lock monitor
T4 is working...
current thread release lock: T4
T2 get the lock monitor
T2 is working...
current thread release lock: T2
T3 get the lock monitor
T3 is working...
current thread release lock: T3
Disconnected from the target VM, address: '127.0.0.1:60952', transport: 'socket'
```


----
- 存在的问题分析
```java
package com.xp.demo.thread.Lock;

import java.util.Arrays;
import java.util.Optional;

public class LockTest {
    public static void main(String[] args) {
        BooleanLock booleanLock = new BooleanLock(false);
        Arrays.asList("T1", "T2", "T3", "T4").stream().forEach(t -> {
            new Thread(() -> {
                try {
                    booleanLock.lock();
                    Optional.of(Thread.currentThread().getName() + " get the lock monitor").ifPresent(System.out::println);
                    work();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    booleanLock.unlock();
                }
            }, t).start();
        });

        //******************************        
        //在这里释放锁会有问题
        booleanLock.unlock();
        //******************************

    }


    public static final void work() throws InterruptedException {
        Optional.of(Thread.currentThread().getName() + " is working...").ifPresent(System.out::println);
        Thread.sleep(5_000);
    }

}

```

``问题代码运行结果分析: ``
```text
T1 get the lock monitor
current thread release lock: main
T2 get the lock monitor
T1 is working...
T2 is working...
current thread release lock: T1
T3 get the lock monitor
current thread release lock: T2
T3 is working...
T4 get the lock monitor
T4 is working...
current thread release lock: T3
current thread release lock: T4
锁被主要线程释放掉了。谁加锁谁释放！！！！解铃还须系铃人

```

``解决问题：记录当前线程``
```java
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

    //**************************
    private Thread current;
    //**************************

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
        //**************************
        current = Thread.currentThread();
        //**************************
        //从blockedThreadCollection删除
        blockedThreadCollection.remove(Thread.currentThread());
    }

    @Override
    public void lock(long time) throws InterruptedException, TimeoutException {

    }

    @Override
    public synchronized void unlock() {
        //**************************
        if(Thread.currentThread() == current){
            initValue = false;
            Optional.of("current thread release lock: " + Thread.currentThread().getName()).ifPresent(System.out::println);
            this.notifyAll();
        }
        //**************************
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

```

``运行结果``
```java
T1 get the lock monitor
T1 is working...
current thread release lock: T1
T4 get the lock monitor
T4 is working...
current thread release lock: T4
T2 get the lock monitor
T2 is working...
current thread release lock: T2
T3 get the lock monitor
T3 is working...
current thread release lock: T3

```