package day14;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Demo02_ForkJoinAction {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        // 提交无返回值任务
        pool.invoke(new PrintTask(1, 10));
    }
}

// 继承 RecursiveAction 表示无返回值任务
class PrintTask extends RecursiveAction {
    private final int start;
    private final int end;

    public PrintTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        // 如果任务足够小，直接执行
        if (end - start <= 3) {
            for (int i = start; i <= end; i++) {
                System.out.println(Thread.currentThread().getName() + " 打印: " + i);
            }
        } else {
            // 任务太大，拆分
            int mid = (start + end) / 2;
            PrintTask left = new PrintTask(start, mid);
            PrintTask right = new PrintTask(mid + 1, end);

            // 优化点：使用 invokeAll 代替 手动 fork 两次 + join 两次
            // 底层会自动调度，效率更高
            invokeAll(left, right);
        }
    }
}