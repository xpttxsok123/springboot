package com.xp.demo.thread;

public class JMMDemo {

    public static  boolean initFlag = false;

    public static void refresh() {

        System.out.println("refresh data...");
        initFlag = true;
        System.out.println("refresh data success...");
    }

    public static void loadData() {
        while (!initFlag) {

        }
        String threadName = Thread.currentThread().getName();

        System.out.println(threadName);
        System.out.println("当前线程" + threadName + "嗅探到 initFlag 的值改变为：" + initFlag);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> { loadData(); });
        Thread thread2 = new Thread(() -> { refresh(); });

        thread1.start();
        Thread.currentThread().sleep(1000);
        thread2.start();
    }

}
