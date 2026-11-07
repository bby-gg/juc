package day05;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 演示：SimpleDateFormat 不是线程安全的
 *
 * 多个线程共用一个 SimpleDateFormat 并发 format，可能抛异常或结果错乱。
 * 解决办法：每个线程自己 new 一个，或改用不可变的 DateTimeFormatter。
 */
public class DateFormatDemo {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 共享，危险

    public static void main(String[] args) throws InterruptedException {
        // 错误：共用同一个 sdf
        Runnable wrong = () -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    sdf.format(new Date());
                } catch (Exception e) {
                    System.out.println(Thread.currentThread().getName() + " 出错: " + e.getClass().getSimpleName());
                }
            }
        };
        Thread t1 = new Thread(wrong, "线程1");
        Thread t2 = new Thread(wrong, "线程2");
        t1.start(); t2.start();
        t1.join(); t2.join();
        System.out.println("共用 sdf 执行完，看上面有没有出错");

        // 正确一：每个线程 new 自己的
        Runnable right1 = () -> {
            SimpleDateFormat local = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < 1000; i++) {
                local.format(new Date());
            }
        };
        Thread t3 = new Thread(right1, "线程3");
        Thread t4 = new Thread(right1, "线程4");
        t3.start(); t4.start();
        t3.join(); t4.join();
        System.out.println("各用各的 sdf：安全");

        // 正确二：DateTimeFormatter，不可变、线程安全
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Runnable right2 = () -> {
            for (int i = 0; i < 1000; i++) {
                fmt.format(LocalDateTime.now());
            }
        };
        Thread t5 = new Thread(right2, "线程5");
        Thread t6 = new Thread(right2, "线程6");
        t5.start(); t6.start();
        t5.join(); t6.join();
        System.out.println("DateTimeFormatter：安全");
    }
}