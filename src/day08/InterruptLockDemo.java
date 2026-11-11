package day08;

import java.util.concurrent.locks.ReentrantLock;

public class InterruptLockDemo {
    static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        // T1 先占住锁
        new Thread(() -> {
            lock.lock();
            try { Thread.sleep(5000); } catch (InterruptedException e) {}
            finally { lock.unlock(); }
        }, "T1").start();

        Thread t2 = new Thread(() -> {
            try {
                System.out.println("T2 尝试可打断加锁");
                lock.lockInterruptibly();   // ★ 可被打断
                try { System.out.println("T2 拿到锁"); }
                finally { lock.unlock(); }
            } catch (InterruptedException e) {
                System.out.println("T2 被中断，放弃等待");
            }
        }, "T2");
        t2.start();

        Thread.sleep(500);
        t2.interrupt();                     // ★ 打断 T2
    }
}