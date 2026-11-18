package day11;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo02_CachedThreadPool {
    public static void main(String[] args) {
        // 1. 创建一个带缓冲的线程池
        ExecutorService pool = Executors.newCachedThreadPool();

        // 2. 提交 10 个任务（由于任务很快，线程池会复用空闲线程，或者创建新线程）
        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            pool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " 执行任务 " + taskId);
                try {
                    Thread.sleep(200); // 模拟短暂耗时
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        // 3. 关闭线程池
        pool.shutdown();
    }
}