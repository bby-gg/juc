package day09;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 对比演示：为什么"读-改-写"不是原子的，而 CAS 是。
 * 关键：在"读"和"写"之间 sleep，让另一个线程插进来改值。
 */
public class CasDemo {

    static int plain = 0;                        // 普通变量
    static AtomicInteger cas = new AtomicInteger(0); // CAS 原子变量

    public static void main(String[] args) throws InterruptedException {
        // 两个线程，各做一次"读到值后 +1 写回"，中间睡 100ms 让另一个线程插队
        Thread a = new Thread(() -> doOnce("A"));
        Thread b = new Thread(() -> doOnce("B"));
        a.start(); b.start();
        a.join();  b.join();

        System.out.println("\n最终结果：");
        System.out.println("普通变量 plain = " + plain + "   （期望2，实际可能是1 -> 丢失更新）");
        System.out.println("CAS 变量 cas   = " + cas.get() + "   （始终是2 -> 原子）");
    }

    static void doOnce(String name) {
        try {
            // ===== 普通变量的读-改-写 =====
            int p = plain;                       // 读
            System.out.println(name + " 读到 plain = " + p);
            Thread.sleep(100);                   // 空档！另一个线程会在这插进来
            plain = p + 1;                       // 写回（用的是旧值 p+1）
            System.out.println(name + " 写回 plain = " + plain);

            // ===== CAS 的读-CAS 循环 =====
            while (true) {
                int c = cas.get();               // 读
                System.out.println(name + " 读到 cas = " + c);
                Thread.sleep(100);               // 同样的空档
                if (cas.compareAndSet(c, c + 1)) { // 比较：内存值还是 c 吗？是才写入 c+1
                    System.out.println(name + " CAS 成功，cas = " + cas.get());
                    break;
                } else {
                    System.out.println(name + " CAS 失败（值已被别人改），重读重试");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}