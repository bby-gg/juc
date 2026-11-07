package day07;

/**
 * 演示：WAITING（wait，_WaitSet）vs BLOCKED（抢锁失败，_EntryList）
 *
 * 线程A：拿锁后 wait() → 释放锁、进 _WaitSet、状态 WAITING
 * 线程B：抢锁失败 → 进 _EntryList、状态 BLOCKED
 *
 * 运行后 jstack <pid> 看：
 *   线程A: WAITING (on object monitor) + waiting on condition
 *   线程B: BLOCKED (on object monitor) + waiting to lock
 */
public class StateDemo {
    static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        // 线程A：先拿锁，然后 wait（释放锁，进 _WaitSet）
        Thread tA = new Thread(() -> {
            synchronized (LOCK) {
                System.out.println("线程A 拿锁，调用 wait");
                try { LOCK.wait(); } catch (InterruptedException e) { }
            }
        }, "线程A");

        tA.start();
        Thread.sleep(500); // 确保线程A 已进入 wait

        // 线程B：拿锁后 sleep，持有锁
        Thread tB = new Thread(() -> {
            synchronized (LOCK) {
                System.out.println("线程B 拿锁，sleep 10 秒");
                try { Thread.sleep(10_000); } catch (InterruptedException e) { }
            }
        }, "线程B");

        // 线程C：抢锁失败，BLOCKED
        Thread tC = new Thread(() -> {
            System.out.println("线程C 尝试抢锁");
            synchronized (LOCK) {
                System.out.println("线程C 拿到锁");
            }
        }, "线程C");

        tB.start();
        Thread.sleep(500); // 确保线程B 持锁
        tC.start();        // 线程C 抢锁失败 → BLOCKED

        Thread.sleep(113000); // 留时间 jstack
        System.out.println("请在此期间执行 jstack 查看线程状态");
        Thread.sleep(7000);

        synchronized (LOCK) { LOCK.notifyAll(); } // 唤醒线程A
        tA.join(); tB.join(); tC.join();
        System.out.println("结束");
    }
}