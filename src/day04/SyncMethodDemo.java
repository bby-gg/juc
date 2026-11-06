package day04;

/**
 * 演示：synchronized 修饰实例方法
 *
 * 实例同步方法等价于 synchronized (this) { ... }，
 * 锁的是【调用该方法的对象】。
 *
 * 本例只创建一个 SyncMethodDemo 对象 demo，
 * 两个线程都调用 demo 的同步方法，锁是同一个 this，互斥生效。
 *
 * 关键点：如果 new 两个对象分别调用，锁就不是同一个 this，互斥失效。
 */
public class SyncMethodDemo {

    // 共享变量
    static int count = 0;

    // 实例同步方法：锁的是当前对象 this
    public synchronized void increment() {
        count++;
    }

    // 实例同步方法：锁的同样是当前对象 this（和 increment 是同一把锁）
    public synchronized void decrement() {
        count--;
    }

    public static void main(String[] args) throws InterruptedException {

        // 只创建一个对象，保证两个线程锁的是同一个 this
        SyncMethodDemo demo = new SyncMethodDemo();

        Thread addThread = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                demo.increment();
            }
        }, "加线程");

        Thread subThread = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                demo.decrement();
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