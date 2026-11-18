package day11;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo01_FixedThreadPool {
    public static void main(String[] args) {
        // 1. 创建一个固定大小为 3 的线程池
        ExecutorService pool = Executors.newFixedThreadPool(3);

        // 2. 提交 5 个任务
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            pool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " 正在执行任务 " + taskId);
                try {
                    Thread.sleep(1000); // 模拟任务耗时
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        // 3. 关闭线程池
        pool.shutdown();
    }
}