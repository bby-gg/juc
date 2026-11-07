package day06;

/**
 * 演示：卖票（synchronized 互斥）
 *
 * 3 个窗口卖 100 张票，不加锁时 ticket-- 不是原子操作，会超卖或重卖。
 * 加锁后，同一时刻只有一个窗口能扣票，结果正确。
 *
 * 对比：把 synchronized 去掉再跑，观察出错。
 */
public class TicketDemo {

    static int ticket = 100;              // 剩余票数（共享变量）
    static final Object LOCK = new Object(); // 锁对象

    public static void main(String[] args) throws InterruptedException {
        Runnable sellTask = () -> {
            while (true) {
                synchronized (LOCK) {          // 临界区：检查 + 扣票必须整体包住
                    if (ticket <= 0) {
                        break;
                    }
                    ticket--;
                    System.out.println(Thread.currentThread().getName()
                            + " 卖出一张，剩余 " + ticket);
                }
            }
        };

        Thread w1 = new Thread(sellTask, "窗口1");
        Thread w2 = new Thread(sellTask, "窗口2");
        Thread w3 = new Thread(sellTask, "窗口3");

        w1.start(); w2.start(); w3.start();
        w1.join(); w2.join(); w3.join();

        System.out.println("最终剩余票数 = " + ticket + "（应为 0）");
    }
}