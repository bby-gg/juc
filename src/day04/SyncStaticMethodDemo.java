package day04;

/**
 * 演示：synchronized 修饰静态方法
 *
 * 静态同步方法等价于 synchronized (类名.class) { ... }，
 * 锁的是【类的 Class 对象】，而不是某个实例。
 *
 * 关键点：静态方法锁（Class 对象）和实例方法锁（this）是两把不同的锁，
 * 二者之间不互斥。
 */
public class SyncStaticMethodDemo {

    // 共享变量
    static int count = 0;

    // 静态同步方法：锁的是 SyncStaticMethodDemo.class
    public static synchronized void add() {
        count++;
    }

    // 静态同步方法：锁的同样是 SyncStaticMethodDemo.class（同一把锁）
    public static synchronized void sub() {
        count--;
    }

    public static void main(String[] args) throws InterruptedException {

        Thread addThread = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                add();
            }
        }, "加线程");

        Thread subThread = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                sub();
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