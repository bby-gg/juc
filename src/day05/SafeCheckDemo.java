package day05;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示：分析一个类安不安全，看三点
 *
 * 1. safeAdd：共享变量加了锁 → 安全
 * 2. localVar：局部变量不逃逸 → 安全
 * 3. escapeList：返回局部变量，引用逃逸 → 不安全
 */
public class SafeCheckDemo {

    private int count = 0;
    private final Object lock = new Object();

    // 安全：加锁
    public void safeAdd() {
        synchronized (lock) {
            count++;
        }
    }

    // 安全：局部变量不逃逸
    public int localVar() {
        int temp = 10;
        return temp + 1;
    }

    // 不安全：返回 list，引用逃逸
    public List<Integer> escapeList() {
        return new ArrayList<>();
    }

    public static void main(String[] args) throws InterruptedException {
        SafeCheckDemo demo = new SafeCheckDemo();

        // 加锁后安全
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) demo.safeAdd();
        }, "线程1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) demo.safeAdd();
        }, "线程2");
        t1.start(); t2.start();
        t1.join(); t2.join();
        System.out.println("加锁后 count = " + demo.count + "（预期 10000）");

        // 逃逸后共享，不安全
        List<Integer> shared = demo.escapeList();
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) shared.add(i);
        }, "线程3");
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) shared.add(i);
        }, "线程4");
        t3.start(); t4.start();
        t3.join(); t4.join();
        System.out.println("逃逸 list 大小 = " + shared.size() + "（预期 2000，可能更小）");
    }
}