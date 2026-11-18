package day14;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Demo01_ForkJoinSum {
    public static void main(String[] args) {
        // 1. 创建 ForkJoin 线程池
        ForkJoinPool pool = new ForkJoinPool();

        // 2. 创建大任务：计算 1 到 10000 的和
        SumTask task = new SumTask(1, 10000);

        // 3. 提交任务并获取结果
        Integer result = pool.invoke(task);

        System.out.println("最终计算结果: " + result);
    }
}

// 继承 RecursiveTask 表示有返回值的任务（RecursiveAction 表示无返回值）
class SumTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 1000; // 阈值：拆分的最小粒度
    private final int start;
    private final int end;

    public SumTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        // 1. 如果任务足够小，直接计算
        if (end - start <= THRESHOLD) {
            int sum = 0;
            for (int i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }

        // 2. 如果任务太大，拆分成两个子任务
        int mid = (start + end) / 2;
        SumTask leftTask = new SumTask(start, mid);
        SumTask rightTask = new SumTask(mid + 1, end);

        // 3. 分别执行子任务 (fork 是异步提交)
        leftTask.fork();
        rightTask.fork();

        // 4. 等待子任务执行完，合并结果 (join 是阻塞获取结果)
        return leftTask.join() + rightTask.join();
    }
}