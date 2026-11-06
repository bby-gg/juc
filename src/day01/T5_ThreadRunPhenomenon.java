package day01;

/**
 * 线程运行现象：
 *  1. 多个线程交替执行，输出顺序不确定
 *  2. 单次输出甚至可能"交错"，因为 println 不是原子的（多跑几次能看到）
 * 结论：不要依赖多线程的执行顺序
 */
public class T5_ThreadRunPhenomenon {

    public static void main(String[] args) {
        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " -> " + i);
            }
        };

        new Thread(task, "A").start();
        new Thread(task, "B").start();
        new Thread(task, "C").start();
    }
}