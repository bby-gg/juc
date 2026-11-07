package day05;

import java.util.ArrayList;
import java.util.List;

/**
 * 演示：局部变量安全，但返回出去（逃逸）后就不安全
 *
 * method1：list 只在方法内用，每个线程各建一个，安全
 * method2：把 list 返回给外面，多个线程拿到同一个 list，不安全
 */
public class LocalVarDemo {

    // 安全：list 不逃逸
    public static void method1() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println(Thread.currentThread().getName() + " 的 list 大小 = " + list.size()); // 永远 10
    }

    // 不安全：把 list 返回出去，引用逃逸
    public static List<Integer> method2() {
        List<Integer> list = new ArrayList<>();
        return list; // 逃逸
    }

    public static void main(String[] args) throws InterruptedException {
        // 情况一：不逃逸，安全
        Thread t1 = new Thread(LocalVarDemo::method1, "线程A");
        Thread t2 = new Thread(LocalVarDemo::method1, "线程B");
        t1.start(); t2.start();
        t1.join(); t2.join();

        // 情况二：逃逸后被两个线程共享，不安全
        List<Integer> shared = method2();
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) shared.add(i);
        }, "线程C");
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) shared.add(i);
        }, "线程D");
        t3.start(); t4.start();
        t3.join(); t4.join();
        System.out.println("共享 list 大小 = " + shared.size() + "（预期 2000，常更小）");
    }
}