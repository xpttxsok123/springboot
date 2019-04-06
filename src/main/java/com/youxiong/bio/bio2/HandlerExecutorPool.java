package com.youxiong.bio.bio2;

import java.util.concurrent.*;

/**
 * @ClassName HandlerExecutorPool
 * @Description TODO
 * @Author
 * @Date 2019/3/23 14:59
 **/
public class HandlerExecutorPool {

    private ExecutorService executorService;


    public HandlerExecutorPool(int maxPoolSize, int queeSize) {
        this.executorService = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                maxPoolSize,
                120L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(queeSize)
        );
    }

    public void execute(Runnable runnable) {
        runnable.run();
    }

}
