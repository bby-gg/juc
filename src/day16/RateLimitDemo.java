package day16;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 接口限流：最多允许 2 个线程同时调用
 */
public class RateLimitDemo {

    public static void main(String[] args) {
        Semaphore limit = new Semaphore(2);   // 同时最多 2 个
        ExecutorService pool = Executors.newFixedThreadPool(5);

        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            pool.submit(() -> {
                try {
                    limit.acquire();
                    System.out.printf("[任务%d] 开始执行，剩余许可=%d%n",
                            taskId, limit.availablePermits());
                    Thread.sleep(1000);   // 模拟调用接口
                    System.out.printf("[任务%d] 执行完成%n", taskId);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    limit.release();
                }
            });
        }

        pool.shutdown();
        try {
            pool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("所有任务完成");
    }
}