package day06;

/**
 * 演示：把慢操作（println）移出临界区，临界区变短
 *
 * 和 TicketDemo 的唯一区别：扣票在锁内，打印在锁外。
 * 临界区只剩"检查 + 扣票"两条极快指令，持锁时间短，
 * 三个窗口会明显交替卖票，而不是一个窗口连续垄断。
 *
 * 运行后对比 TicketDemo 的输出，观察"窗口交替"的差异。
 */
public class TicketShortDemo {

    static int ticket = 100;                 // 剩余票数（共享变量）
    static final Object LOCK = new Object(); // 锁对象

    public static void main(String[] args) throws InterruptedException {
        Runnable sellTask = () -> {
            while (true) {
                int sold;                       // 记录本次卖出的票号
                synchronized (LOCK) {           // 临界区开始：极短
                    if (ticket <= 0) {
                        break;
                    }
                    ticket--;
                    sold = ticket;              // 只记录结果，不打印
                }                               // 临界区结束
                // 打印在锁外，不占锁，不拉长临界区
                System.out.println(Thread.currentThread().getName()
                        + " 卖出一张，剩余 " + sold);
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