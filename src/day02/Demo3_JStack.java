package day02;

/**
 * 运行后另开窗口执行 jstack <pid>，找 my-xxx 开头的线程看状态。
 * pid 用 jps -l 查。
 */
public class Demo3_JStack {

    public static void main(String[] args) throws Exception {
        Object lock = new Object();

        new Thread(() -> { while (true) {} }, "my-busy").start();                 // RUNNABLE
        new Thread(() -> {
            try { Thread.sleep(Long.MAX_VALUE); } catch (InterruptedException e) {}
        }, "my-sleep").start();                                                  // TIMED_WAITING

        new Thread(() -> {
            synchronized (lock) {
                try { lock.wait(); } catch (InterruptedException e) {}
            }
        }, "my-wait").start();                                                   // WAITING

        new Thread(() -> {
            synchronized (lock) {
                try { Thread.sleep(Long.MAX_VALUE); } catch (InterruptedException e) {}
            }
        }, "my-holder").start();                                                 // 占锁

        Thread.sleep(100);

        new Thread(() -> {
            synchronized (lock) {} // 永远拿不到锁
        }, "my-blocked").start();                                                // BLOCKED

        System.out.println("用 jps -l 找到 PID，再执行 jstack <pid>");
        Thread.sleep(Long.MAX_VALUE);
    }
}