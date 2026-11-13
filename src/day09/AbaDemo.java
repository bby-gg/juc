package day09;

import java.util.concurrent.atomic.AtomicReference;

public class AbaDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicReference<String> ref = new AtomicReference<>("A");

        Thread t1 = new Thread(() -> {
            String old = ref.get();                    // 读到 A
            System.out.println("线程1 读到 = " + old);
            sleep(200);
            boolean ok = ref.compareAndSet(old, "C");  // 以为是 A->C
            System.out.println("线程1 CAS(A->C) = " + ok + "，现在 = " + ref.get());
        });

        Thread t2 = new Thread(() -> {
            sleep(50);
            ref.compareAndSet("A", "B");               // A -> B
            System.out.println("线程2 A->B，现在 = " + ref.get());
            ref.compareAndSet("B", "A");               // B -> A
            System.out.println("线程2 B->A，现在 = " + ref.get());
        });

        t1.start(); t2.start();
        t1.join(); t2.join();
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}