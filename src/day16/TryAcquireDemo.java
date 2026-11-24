package day16;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * tryAcquire 用法：拿不到就不等 / 只等几秒
 */
public class TryAcquireDemo {

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);

        // 1. 先占住唯一的一个许可
        semaphore.acquire();
        System.out.println("主线程占住了唯一的许可");

        // 2. 尝试获取（不等待，立刻返回）
        boolean got = semaphore.tryAcquire();
        System.out.println("tryAcquire() 立刻返回: " + got);  // false

        // 3. 尝试获取，最多等 1 秒
        boolean got2 = semaphore.tryAcquire(1, TimeUnit.SECONDS);
        System.out.println("tryAcquire(1s) 超时返回: " + got2);  // false

        // 4. 释放许可，再尝试
        semaphore.release();
        boolean got3 = semaphore.tryAcquire();
        System.out.println("释放后 tryAcquire() 返回: " + got3);  // true

        semaphore.release();
    }
}