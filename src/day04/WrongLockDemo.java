package day04;

/**
 * 演示：锁对象不同导致 synchronized 失效（常见坑）
 *
 * 两个线程分别锁 lock1 和 lock2，这是两个不同的对象，
 * 相当于各锁各的，互斥不成立，count++ 和 count-- 仍会交错执行。
 *
 * 结论：synchronized 锁的是【括号里的对象】，
 *      对象不同 → 不是同一把锁 → 互不干扰。
 */
public class WrongLockDemo {

    // 共享变量
    static int count = 0;

    public static void main(String[] args) throws InterruptedException {

        // 两个不同的锁对象
        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (lock1) { // 锁的是 lock1
                    count++;
                }
            }
        }, "线程1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (lock2) { // 锁的是 lock2，和 t1 不是同一把锁
                    count--;
                }
            }
        }, "线程2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        // 结果不可靠，往往不是 0
        System.out.println("最终 count = " + count);
    }
}