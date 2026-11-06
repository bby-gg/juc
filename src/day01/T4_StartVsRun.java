package day01;

/**
 * start() vs run() 的本质区别：
 *   run()  —— 普通方法调用，在"当前线程"里同步执行，不会创建新线程
 *   start()—— 通知 JVM 创建新线程，由新线程去回调 run()
 */
public class T4_StartVsRun {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("run() 执行线程: " + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        System.out.println("main 线程: " + Thread.currentThread().getName());

        // 1. 直接调 run()：在 main 线程里执行
        MyThread t1 = new MyThread();
        t1.run(); // 输出 run() 执行线程: main

        // 2. 调 start()：新线程执行
        MyThread t2 = new MyThread();
        t2.start(); // 输出 run() 执行线程: Thread-0（或类似）

        // 3. start() 不能调两次：第二次抛 IllegalThreadStateException
        try {
            t2.start();
        } catch (IllegalThreadStateException e) {
            System.out.println("第二次 start() 抛异常: " + e.getClass().getSimpleName());
        }
    }
}