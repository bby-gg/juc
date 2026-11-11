public class WaitNotifyDemo {
    static final Object lock = new Object();
    static boolean hasData = false;

    public static void main(String[] args) {
        // 消费者
        new Thread(() -> {
            synchronized (lock) {
                while (!hasData) {          // ★ 必须用 while，不能用 if
                    try {
                        System.out.println("消费者等待");
                        lock.wait();
                    } catch (InterruptedException e) {}
                }
                System.out.println("消费者消费");
            }
        }, "Consumer").start();

        // 生产者
        new Thread(() -> {
            synchronized (lock) {
                hasData = true;
                lock.notifyAll();           // ★ 用 notifyAll，别用 notify
                System.out.println("生产者通知");
            }
        }, "Producer").start();
    }
}