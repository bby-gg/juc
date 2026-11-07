package day07;

/**
 * 演示：sleep 不释放锁，wait 释放锁
 *
 * 线程1 持锁 sleep 3 秒，线程2 想进同步块必须等线程1 结束，
 * 证明 sleep 期间锁没释放。
 *
 * 对比 WaitNotifyDemo：那里 wait() 后生产者能立刻进锁，证明 wait 释放锁。
 */
public class SleepVsWaitDemo {
    static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (LOCK) {
                System.out.println("线程1 持锁 sleep 3 秒（不释放锁）");
                try { Thread.sleep(3000); } catch (InterruptedException e) { }
                System.out.println("线程1 sleep 结束，释放锁");
            }
        }, "线程1");

        Thread t2 = new Thread(() -> {
            System.out.println("线程2 想进同步块...");
            synchronized (LOCK) {
                System.out.println("线程2 进来了（说明线程1 已释放锁）");
            }
        }, "线程2");

        t1.start();
        Thread.sleep(100);   // 确保线程1 先持锁
        t2.start();
        t1.join(); t2.join();
        System.out.println("结论：sleep 不释放锁，线程2 等了 3 秒才进来；wait 会释放锁");
    }
}