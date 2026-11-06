package day02;

/**
 * 对比：1 个线程 vs 200 个线程跑同样的活。
 * 多跑几次看耗时波动，理解"线程多了有切换开销"。
 */
public class Demo2_ContextSwitch {

    static final int LOOPS = 1_000_000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("CPU 核数: " + Runtime.getRuntime().availableProcessors());
        System.out.println("1 个线程耗时:   " + run(1) + " ms");
        System.out.println("200 个线程耗时: " + run(200) + " ms");
    }

    static long run(int count) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread[] ts = new Thread[count];
        for (int i = 0; i < count; i++) {
            ts[i] = new Thread(() -> {
                long sum = 0;
                for (int j = 0; j < LOOPS; j++) sum += j;
            });
            ts[i].start();
        }
        for (Thread t : ts) t.join();
        return System.currentTimeMillis() - start;
    }
}