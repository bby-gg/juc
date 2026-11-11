public class TwoLocksDemo {
    static final Object lockA = new Object();
    static final Object lockB = new Object();

    public static void main(String[] args) {
        // 线程1 只操作 A
        new Thread(() -> {
            synchronized (lockA) {
                System.out.println("T1 拿到 A");
                sleep(2000);
            }
        }, "T1").start();

        // 线程2 只操作 B —— 用两把锁，可以并行
        new Thread(() -> {
            synchronized (lockB) {
                System.out.println("T2 拿到 B");
                sleep(2000);
            }
        }, "T2").start();
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
}