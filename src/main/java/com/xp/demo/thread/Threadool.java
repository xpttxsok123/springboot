package com.xp.demo.thread;

import org.junit.Test;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * CPU密集：  CPU核数 + 1     尽量减少线程切换
 * IO密集：   CPU核心数/(1 - 阻塞系数)   阻塞系数为0.8-0.9   IO具有大量的阻塞
 */
public class Threadool {


    //1.CPU core
    @Test
    public void test_cpu_core() {
        int coreSize = Runtime.getRuntime().availableProcessors();
        System.out.println(coreSize);
    }

    //2.线程不允许用Executors去创建，而是用ThreadPoolExecutor的方式。线程必须由线程池提供，不可以直接常见线程


    /**
     * public ThreadPoolExecutor(
     * int corePoolSize,
     * int maximumPoolSize,
     * long keepAliveTime,
     * TimeUnit unit,
     * BlockingQueue<Runnable> workQueue,
     * ThreadFactory threadFactory,
     * RejectedExecutionHandler handler)
     * {
     * ........
     * }
     * <p>
     * 线程池拒绝策略
     * <p>
     * CallerRunsPolicy         : 调用者运行机制
     * DiscardOldestPolicy      : 丢弃队列中最老的任务
     * DiscardPolicy            : 丢弃新的任务
     * AbortPolicy              : 直接拒绝，会抛异常
     */
    @Test
    public void test_reject_hander() {



        //ThreadPoolExecutor.AbortPolicy
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                1,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                //Executors.defaultThreadFactory(),
                //new ThreadPoolExecutor.AbortPolicy()//  maximum + queueSize < taskNum  throw Exception ===>  AbortPolicy
                new ThreadPoolExecutor.CallerRunsPolicy() // 多出来的任务有父线程执行任务
                //new ThreadPoolExecutor.DiscardOldestPolicy()
                //new ThreadPoolExecutor.DiscardPolicy()
        );

        try{

            for (int i = 0; i< 10; i++){
                threadPoolExecutor.execute(() -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + "\t" + "办理业务");
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPoolExecutor.shutdown();
        }


        System.out.println("===========game over==========");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
