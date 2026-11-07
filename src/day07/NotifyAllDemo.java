package day07;

/**
 * 演示：notify 只唤醒一个，可能唤醒错误线程导致死等；notifyAll 唤醒全部，安全
 *
 * 两个消费者都在等"队列非空"。
 * 生产者用 notify() 只唤醒一个消费者，如果唤醒的消费者条件仍不满足，
 * 它又 wait 回去，而真正该被唤醒的没被唤醒 → 可能死等。
 *
 * 本例用两个条件不同的等待者放大问题：
 * - 消费者1 等"非空"
 * - 消费者2 等"非空"
 * 生产者放 1 个后 notify()，只唤醒一个；如果那个消费完，
 * 另一个可能一直等不到。用 notifyAll() 两个都被唤醒，配合 while 重检，安全。
 */
public class NotifyAllDemo {

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        int[] queue = new int[1];   // 容量 1

        Thread c1 = new Thread(() -> consume(lock, queue, "消费者1"), "消费者1");
        Thread c2 = new Thread(() -> consume(lock, queue, "消费者2"), "消费者2");
        c1.setDaemon(true);
        c2.setDaemon(true);
        c1.start();
        c2.start();

        Thread.sleep(200); // 让两个消费者都进入 wait

        for (int i = 1; i <= 3; i++) {
            synchronized (lock) {
                while (queue[0] != 0) {
                    try { lock.wait(); } catch (InterruptedException e) { }
                }
                queue[0] = i;
                System.out.println("生产者放入 " + i);
                lock.notifyAll();   // 改成 notify() 多跑几次，可能死等
            }
            Thread.sleep(300);
        }
        Thread.sleep(1000);
        System.out.println("结束");
    }

    static void consume(Object lock, int[] queue, String name) {
        while (true) {
            synchronized (lock) {
                while (queue[0] == 0) {
                    try { lock.wait(); } catch (InterruptedException e) { }
                }
                int v = queue[0];
                queue[0] = 0;
                System.out.println(name + " 消费 " + v);
                lock.notifyAll();
            }
        }
    }
}