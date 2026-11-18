package day11;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo03_SingleThreadPool {
    public static void main(String[] args) {
        // 1. 创建只有一个线程的线程池
        ExecutorService pool = Executors.newSingleThreadExecutor();

        // 2. 提交 3 个任务
        for (int i = 1; i <= 3; i++) {
            final int taskId = i;
            pool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " 正在执行任务 " + taskId);
                try {
                    Thread.sleep(500); // 模拟耗时，证明任务是按顺序执行的
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        // 3. 关闭线程池
        pool.shutdown();
    }
}