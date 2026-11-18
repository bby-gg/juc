package day12;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Demo02_InvokeAll {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        // 准备 3 个任务
        List<Callable<String>> tasks = Arrays.asList(
                () -> { Thread.sleep(500); return "任务A完成"; },
                () -> { Thread.sleep(1000); return "任务B完成"; },
                () -> { Thread.sleep(1500); return "任务C完成"; }
        );

        // invokeAll 会等所有任务都执行完，才继续往下走
        System.out.println("开始批量提交...");
        List<Future<String>> futures = pool.invokeAll(tasks);

        // 遍历获取结果
        for (Future<String> f : futures) {
            System.out.println(f.get());
        }
        System.out.println("所有任务执行完毕");

        pool.shutdown();
    }
}