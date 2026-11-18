package day13;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Demo01_ScheduledPool {
    public static void main(String[] args) {
        // 创建包含 2 个核心线程的定时任务池
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

        // 1. 延时执行一次：延迟 2 秒后执行
        pool.schedule(() -> System.out.println("延时任务：2秒后执行完毕"), 2, TimeUnit.SECONDS);

        // 2. 周期性执行：延迟 1 秒后开始，每 2 秒执行一次
        // 注意：如果任务抛出异常，周期执行会终止！
        pool.scheduleAtFixedRate(() -> {
            System.out.println("周期任务：" + System.currentTimeMillis());
        }, 1, 2, TimeUnit.SECONDS);

        // 为了演示，主线程不立即退出，否则看不到执行结果
        try { Thread.sleep(7000); } catch (InterruptedException e) {}

        pool.shutdown(); // 关闭池
    }
}