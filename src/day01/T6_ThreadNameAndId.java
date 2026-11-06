package day01;

/**
 * 线程名、ID、优先级 的基础观察，为 Day 2 看线程列表做铺垫
 */
public class T6_ThreadNameAndId {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            System.out.println("线程名: " + Thread.currentThread().getName());
            System.out.println("线程ID: " + Thread.currentThread().getId());
            System.out.println("优先级: " + Thread.currentThread().getPriority());
        }, "自定义线程");
        t.start();

        System.out.println("main 的线程名: " + Thread.currentThread().getName());
        System.out.println("main 的线程ID: " + Thread.currentThread().getId());
    }
}