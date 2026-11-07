package day07;

import java.util.concurrent.locks.LockSupport;

/**
 * 演示：park/unpark 的"许可"机制
 *
 * 和 wait/notify 最大区别：unpark 可以先于 park 调用。
 * 先发一个"许可"，之后 park 直接消费许可、不阻塞。
 * wait/notify 做不到（先 notify 再 wait 会一直等）。
 */
public class ParkDemo {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            System.out.println("线程先 unpark 自己，再 park");
            LockSupport.unpark(Thread.currentThread()); // 先发许可
            LockSupport.park();                          // 消费许可，不阻塞
            System.out.println("没有阻塞，直接到这里");
        });
        t.start();
    }
}