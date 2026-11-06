package day04;

/**
 * 演示：synchronized 同步代码块
 *
 * 用 synchronized (LOCK) 包住临界区（count++ / count--），
 * 保证同一时刻只有一个线程能进入临界区，从而得到正确结果 0。
 *
 * 关键点：两个线程必须锁【同一个对象】LOCK，否则互斥失效。
 *
 * 对比：与 ShareProblemDemo 逻辑相同，只是加了同步，结果稳定为 0。
 */
public class SyncBlockDemo {

    // 共享变量
    static int count = 0;

    // 锁对象：必须是同一个对象才有互斥效果，命名直白
    static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread addThread = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                // 临界区：同一时刻只允许一个线程进入
                synchronized (LOCK) {
                    count++;
                }
            }
        }, "加线程");

        Thread subThread = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (LOCK) {
                    count--;
                }
            }
        }, "减线程");

        addThread.start();
        subThread.start();
        addThread.join();
        subThread.join();

        // 稳定输出 0
        System.out.println("最终 count = " + count);
    }
}