package day15;

import java.util.concurrent.locks.StampedLock;

public class Point {

    private double x, y;
    private final StampedLock lock = new StampedLock();

    // 写：加写锁
    public void move(double dx, double dy) {
        long stamp = lock.writeLock();
        try {
            x += dx;
            y += dy;
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    // 读：乐观读（不加锁）
    public double distance() {
        long stamp = lock.tryOptimisticRead();  // 1. 拿个版本号
        double cx = x, cy = y;                  // 2. 读数据到局部变量
        if (!lock.validate(stamp)) {            // 3. 检查期间有没有人写
            stamp = lock.readLock();            // 4. 有人写了，加读锁重读
            try {
                cx = x;
                cy = y;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        return Math.sqrt(cx * cx + cy * cy);
    }
}