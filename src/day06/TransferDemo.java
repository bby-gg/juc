package day06;

/**
 * 演示：转账交叉加锁导致死锁（多把锁的坑）
 *
 * 线程1：A 转 B，先锁 A 再锁 B
 * 线程2：B 转 A，先锁 B 再锁 A
 * 两个线程各持一把锁、等对方释放，形成死锁。
 *
 * 运行后程序会卡住，用 jstack <pid> 能看到 "Found one Java-level deadlock"。
 *
 * 解决思路（Day 8 展开）：固定加锁顺序，按对象 id/hash 排序后加锁。
 */
public class TransferDemo {

    static class Account {
        String name;
        int balance;
        Account(String name, int balance) {
            this.name = name;
            this.balance = balance;
        }
    }

    // 转账：先锁 from，再锁 to —— 顺序不固定，是死锁根源
    public static void transfer(Account from, Account to, int amount) {
        synchronized (from) {          // 先锁转出账户
            synchronized (to) {        // 再锁转入账户
                from.balance -= amount;
                to.balance += amount;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account("A", 1000);
        Account b = new Account("B", 1000);

        // 线程1：A -> B
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) transfer(a, b, 1);
        }, "线程1(A->B)");

        // 线程2：B -> A（加锁顺序相反，死锁伏笔）
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) transfer(b, a, 1);
        }, "线程2(B->A)");

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("A 余额 = " + a.balance + "，B 余额 = " + b.balance + "，总和 = " + (a.balance + b.balance));
    }
}