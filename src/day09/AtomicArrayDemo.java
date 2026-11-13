package day09;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicArrayDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerArray arr = new AtomicIntegerArray(5); // 长度5，初始全0

        // 单线程基本操作
        arr.set(0, 10);
        System.out.println("arr[0] = " + arr.get(0));
        System.out.println("arr[1] getAndIncrement = " + arr.getAndIncrement(1) + "，现在 = " + arr.get(1));
        System.out.println("arr[1] incrementAndGet = " + arr.incrementAndGet(1) + "，现在 = " + arr.get(1));
        System.out.println("CAS arr[0] 10->100 = " + arr.compareAndSet(0, 10, 100) + "，现在 = " + arr.get(0));
        System.out.println("CAS arr[0] 10->200 = " + arr.compareAndSet(0, 10, 200) + "（失败，值已是100）");

        // 多线程：每个线程只操作自己的下标，互不干扰
        Thread t1 = new Thread(() -> { for (int i = 0; i < 1000; i++) arr.incrementAndGet(2); });
        Thread t2 = new Thread(() -> { for (int i = 0; i < 1000; i++) arr.incrementAndGet(2); });
        t1.start(); t2.start(); t1.join(); t2.join();
        System.out.println("多线程 arr[2] = " + arr.get(2) + "（期望2000）");

        // 打印整个数组
        System.out.print("最终数组 = [");
        for (int i = 0; i < arr.length(); i++) {
            System.out.print(arr.get(i) + (i < arr.length() - 1 ? ", " : ""));
        }
        System.out.println("]");
    }
}