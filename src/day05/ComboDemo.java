package day05;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 演示：单个方法线程安全，不代表组合操作安全
 *
 * 错误写法：先 containsKey 检查，再 put。两步之间可能被别的线程插队。
 * 正确写法：用原子方法 computeIfAbsent，一步完成。
 */
public class ComboDemo {

    static ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        // 错误：检查 + 操作，两步组合，不安全
        Runnable wrong = () -> {
            for (int i = 0; i < 1000; i++) {
                String key = "key" + i;
                if (!map.containsKey(key)) { // 第一步
                    map.put(key, 1);         // 第二步
                }
            }
        };
        Thread t1 = new Thread(wrong, "线程1");
        Thread t2 = new Thread(wrong, "线程2");
        t1.start(); t2.start();
        t1.join(); t2.join();
        System.out.println("组合操作 map 大小 = " + map.size() + "（预期 1000）");

        // 正确：原子方法，一步完成
        ConcurrentHashMap<String, Integer> map2 = new ConcurrentHashMap<>();
        Runnable right = () -> {
            for (int i = 0; i < 1000; i++) {
                map2.computeIfAbsent("key" + i, k -> 1);
            }
        };
        Thread t3 = new Thread(right, "线程3");
        Thread t4 = new Thread(right, "线程4");
        t3.start(); t4.start();
        t3.join(); t4.join();
        System.out.println("原子方法 map2 大小 = " + map2.size() + "（稳定 1000）");
    }
}