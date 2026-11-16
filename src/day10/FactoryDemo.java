package day10;

public class FactoryDemo {
    public static void main(String[] args) {
        // 不用工厂接口，直接给 Thread 取名字
        Thread t1 = new Thread(() -> {
            System.out.println("我是 " + Thread.currentThread().getName() + "，我在干活");
        }, "卖票窗口-1"); // 第二个参数就是名字！

        Thread t2 = new Thread(() -> {
            System.out.println("我是 " + Thread.currentThread().getName() + "，我在干活");
        }, "卖票窗口-2");

        t1.start();
        t2.start();
    }
}