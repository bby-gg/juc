package day01;

/**
 * 方式一：继承 Thread，重写 run()
 * 缺点：Java 单继承，继承 Thread 后不能再继承别的类
 */
public class T1_CreateByThread {

    static class MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                // getName() 就是当前线程名，这里应是 Thread-0
                System.out.println(getName() + " 执行 i=" + i);
            }
        }
    }

    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start(); // 启动新线程
        System.out.println("main 线程结束");
    }
}