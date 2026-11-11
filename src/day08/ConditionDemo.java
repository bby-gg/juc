package day08;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {
    static final ReentrantLock lock = new ReentrantLock();
    static final Condition notEmpty = lock.newCondition();  // ★ 条件变量
    static boolean hasData = false;

    public static void main(String[] args) {
        new Thread(() -> {
            lock.lock();
            try {
                while (!hasData) {
                    System.out.println("消费者 await");
                    notEmpty.await();                   // 类似 wait，但语义更清晰
                }
                System.out.println("消费者消费");
            } catch (InterruptedException e) {}
            finally { lock.unlock(); }
        }, "Consumer").start();

        new Thread(() -> {
            lock.lock();
            try {
                hasData = true;
                notEmpty.signalAll();                   // 类似 notifyAll
                System.out.println("生产者 signalAll");
            } finally { lock.unlock(); }
        }, "Producer").start();
    }
}
