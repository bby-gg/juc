package day01;

/**
 * 方式二：实现 Runnable，交给 Thread 执行
 * 推荐：任务与线程解耦，类还能继承别的类
 */
public class T2_CreateByRunnable {

    static class MyTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println(Thread.currentThread().getName() + " 执行 i=" + i);
            }
        }
    }

    public static void main(String[] args) {
        Runnable task = new MyTask();
        // 同一个任务可以交给多个线程执行（注意任务本身要线程安全）
        new Thread(task, "线程A").start();
        new Thread(task, "线程B").start();
    }
}