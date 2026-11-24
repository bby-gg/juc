package day15;

import java.util.concurrent.locks.StampedLock;

public class SimpleDemo {
    static int count = 0;
    static StampedLock lock = new StampedLock();

    public static void main(String[] args) {
        // ===== 写锁：只能一个线程进 =====
        long stamp = lock.writeLock();
        try {
            count++;
            System.out.println("写锁修改 count = " + count);
        } finally {
            lock.unlockWrite(stamp);   // 用 stamp 解锁
        }

        // ===== 悲观读锁：多个线程可以同时进 =====
        long rs = lock.readLock();
        try {
            System.out.println("读锁读取 count = " + count);
        } finally {
            lock.unlockRead(rs);
        }

        // ===== 乐观读：不加锁，只验证 =====
        long os = lock.tryOptimisticRead();
        int c = count;               // 直接读
        if (lock.validate(os)) {
            System.out.println("乐观读成功 count = " + c);
        } else {
            System.out.println("乐观读失败，有人改了数据");
        }
    }
}