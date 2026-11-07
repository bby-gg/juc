package day07;

/**
 * 演示：条件判断用 while 不用 if（防虚假唤醒）
 *
 * 生产者放 5 个，消费者用 while 判断队列空不空。
 * 被唤醒后重新检查条件，不会消费空队列。
 *
 * 把 while 改成 if 多跑几次，可能消费空队列或卡住（虚假唤醒）。
 */
public class WaitNotifyDemo {

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        int[] queue = new int[1];   // 容量 1，0 表示空
        int[] count = {0};          // 已消费数量

        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                synchronized (lock) {
                    while (queue[0] != 0) {          // 队列满，等
                        try { lock.wait(); } catch (InterruptedException e) { }
                    }
                    queue[0] = i;
                    System.out.println("生产者放入 " + i);
                    lock.notifyAll();
                }
            }
        }, "生产者");

        Thread consumer = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (queue[0] == 0) {          // ← while：被唤醒后重新检查
                        try { lock.wait(); } catch (InterruptedException e) { }
                    }
                    int v = queue[0];
                    queue[0] = 0;
                    count[0]++;
                    System.out.println("消费者消费 " + v);
                    lock.notifyAll();
                    if (count[0] >= 5) break;
                }
            }
        }, "消费者");

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println("结束，共消费 " + count[0] + " 个");
    }
}