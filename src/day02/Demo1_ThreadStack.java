package day02;

/**
 * 用法：在 method3 里打断点（Suspend 设为 Thread），
 * 然后切换 Frames 面板的线程，看每个线程的调用栈是独立的。
 */
public class Demo1_ThreadStack {

    public static void main(String[] args) {
        new Thread(() -> method1("A"), "线程A").start();
        new Thread(() -> method1("B"), "线程B").start();
        method1("main");
    }

    static void method1(String name) { method2(name); }
    static void method2(String name) { method3(name); }

    static void method3(String name) {
        System.out.println(name + " 的栈深度: " + Thread.currentThread().getStackTrace().length);
    }
}