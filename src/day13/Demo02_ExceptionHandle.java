package day13;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Demo02_ExceptionHandle {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(1);

        // 1. 用 execute 提交，抛出异常会打印控制台堆栈
        pool.execute(() -> {
            throw new RuntimeException("execute 任务发生异常");
        });

        // 2. 用 submit 提交，异常被吞了，必须用 future.get() 捕获
        Future<?> future = pool.submit(() -> {
            throw new RuntimeException("submit 任务发生异常");
        });

        try {
            future.get(); // 这里会抛出 ExecutionException 包装的异常
        } catch (Exception e) {
            System.out.println("捕获到 submit 的异常: " + e.getCause().getMessage());
        }

        pool.shutdown();
    }
}