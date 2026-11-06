package day01;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 方式三：实现 Callable + FutureTask
 * 相比 Runnable：有返回值、可抛受检异常
 */
public class T3_CreateByCallable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Callable 是"有返回值的任务"
        Callable<Integer> callable = () -> {
            System.out.println("Callable 在 " + Thread.currentThread().getName() + " 中执行");
            Thread.sleep(1000);
            return 42;
        };

        // FutureTask 既是 Runnable 又是 Future
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        new Thread(futureTask, "计算线程").start();

        // get() 会阻塞，直到任务返回结果
        Integer result = futureTask.get();
        System.out.println("拿到返回值: " + result);
    }
}