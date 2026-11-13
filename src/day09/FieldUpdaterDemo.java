package day09;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class FieldUpdaterDemo {
    static class Counter {
        volatile int count = 0;   // 必须是 volatile，不能 private/static
    }

    // 静态创建一次，复用
    private static final AtomicIntegerFieldUpdater<Counter> UPDATER =
            AtomicIntegerFieldUpdater.newUpdater(Counter.class, "count");

    public static void main(String[] args) throws InterruptedException {
        Counter c = new Counter();

        System.out.println("初始 = " + UPDATER.get(c));
        System.out.println("incrementAndGet = " + UPDATER.incrementAndGet(c));
        System.out.println("CAS(1->10) = " + UPDATER.compareAndSet(c, 1, 10) + "，现在 = " + UPDATER.get(c));
        System.out.println("CAS(1->20) = " + UPDATER.compareAndSet(c, 1, 20) + "（失败，值已是10）");

        // 多线程并发自增
        Runnable task = () -> {
            for (int i = 0; i < 10000; i++) UPDATER.incrementAndGet(c);
        };
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start(); t2.start(); t1.join(); t2.join();
        System.out.println("多线程结果 = " + UPDATER.get(c) + "（期望20000）");
    }
}