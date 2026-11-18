package day12;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo03_InvokeAny {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        // 准备 3 个不同耗时的任务
        List<Callable<String>> tasks = Arrays.asList(
                () -> { Thread.sleep(2000); return "慢速任务完成"; },
                () -> { Thread.sleep(500);  return "快速任务完成"; }, // 这个最先跑完
                () -> { Thread.sleep(1500); return "中速任务完成"; }
        );

        // invokeAny 会阻塞，直到拿到第一个成功的结果
        System.out.println("开始执行...");
        String result = pool.invokeAny(tasks);

        // 只会打印 "快速任务完成"，其余任务被取消
        System.out.println("最先返回的结果: " + result);

        pool.shutdown();
    }
}