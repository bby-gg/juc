package day13;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Demo03_HeartbeatTask {
    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

        // 模拟应用：每隔 2 秒检测一次服务状态
        pool.scheduleAtFixedRate(() -> {
            boolean isAlive = checkService(); // 模拟检测逻辑
            if (!isAlive) {
                System.err.println("警告：服务不可用！");
            } else {
                System.out.println("服务正常运行中...");
            }
        }, 0, 2, TimeUnit.SECONDS);

        // 模拟让程序跑 10 秒后退出
        try { Thread.sleep(10000); } catch (InterruptedException e) {}

        pool.shutdown();
        System.out.println("心跳检测已停止");
    }

    // 简单模拟检测方法
    private static boolean checkService() {
        // 这里可以替换为真实的网络请求、数据库连接等检测代码
        return true; // 假定一直正常运行
    }
}