package day04;

/**
 * 演示：共享问题（竞态条件）
 *
 * 两个线程同时对同一个共享变量 count 做 加 和 减，
 * 各执行 5000 次，理论上最终结果应为 0。
 * 但由于 count++ / count-- 不是原子操作（读-改-写三步），
 * 多线程指令交错会导致结果不可预测，往往不是 0。
 *
 * 运行方式：直接运行 main 方法，多跑几次观察结果。
 */
public class ShareProblemDemo {

    // 共享变量：两个线程都会读写它
    static int count = 0;

    public static void main(String[] args) throws InterruptedException {

        // 加线程：对 count 累加 5000 次
        Thread addThread = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                count++;
            }
        }, "加线程");

        // 减线程：对 count 累减 5000 次
        Thread subThread = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                count--;
            }
        }, "减线程");

        addThread.start();
        subThread.start();

        // 等待两个线程都执行完毕，再打印结果
        addThread.join();
        subThread.join();

        // 预期是 0，实际大概率不是 0
        System.out.println("最终 count = " + count);
    }
}