import java.util.concurrent.locks.ReentrantLock;

public class ReentrantDemo {
    static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
        try {
            System.out.println("第一次加锁");
            lock.lock();                 // ★ 同一线程可重复加锁
            try {
                System.out.println("第二次加锁，holdCount=" + lock.getHoldCount());
            } finally {
                lock.unlock();
            }
        } finally {
            lock.unlock();
        }
        System.out.println("全部释放，isLocked=" + lock.isLocked());
        ReentrantLock lock = new ReentrantLock();

        System.out.println("初始: isLocked=" + lock.isLocked() + ", holdCount=" + lock.getHoldCount());

        lock.lock();
        System.out.println("lock 1: isLocked=" + lock.isLocked() + ", holdCount=" + lock.getHoldCount());

        lock.lock();
        System.out.println("lock 2: isLocked=" + lock.isLocked() + ", holdCount=" + lock.getHoldCount());

        lock.lock();
        System.out.println("lock 3: isLocked=" + lock.isLocked() + ", holdCount=" + lock.getHoldCount());

        lock.unlock();
        System.out.println("unlock 1: isLocked=" + lock.isLocked() + ", holdCount=" + lock.getHoldCount());

        lock.unlock();
        System.out.println("unlock 2: isLocked=" + lock.isLocked() + ", holdCount=" + lock.getHoldCount());

        lock.unlock();
        System.out.println("unlock 3: isLocked=" + lock.isLocked() + ", holdCount=" + lock.getHoldCount());
    }
}