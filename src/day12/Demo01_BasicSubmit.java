package day12;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Demo01_BasicSubmit {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        // 1. execute：无返回值
        pool.execute(() -> System.out.println("execute 执行 Runnable"));

        // 2. submit：有返回值 (Future)
        Future<String> future = pool.submit(() -> {
            Thread.sleep(1000); // 模拟耗时
            return "submit 执行 Callable 并返回结果";
        });

        // future.get() 会阻塞等待任务执行完毕并拿到结果
        System.out.println("获取结果: " + future.get());

        pool.shutdown();
    }
}