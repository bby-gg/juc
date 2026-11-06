package day03;

public class D4_States {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();

        // 1. NEW
        Thread t1 = new Thread(() -> {});
        System.out.println("t1 (NEW): " + t1.getState());

        // 2. RUNNABLE
        Thread t2 = new Thread(() -> { while(true){} });
        t2.start();
        Thread.sleep(100);
        System.out.println("t2 (RUNNABLE): " + t2.getState());

        // 3. BLOCKED (抢不到锁)
        Thread t3_holder = new Thread(() -> {
            synchronized (lock) { try { Thread.sleep(2000); } catch (Exception e) {} }
        });
        t3_holder.start(); Thread.sleep(100);
        Thread t3 = new Thread(() -> { synchronized (lock) {} });
        t3.start(); Thread.sleep(100);
        System.out.println("t3 (BLOCKED): " + t3.getState());

        // 4. WAITING (无限等)
        Thread t4 = new Thread(() -> {
            synchronized (lock) { try { lock.wait(); } catch (Exception e) {} }
        });
        t4.start(); Thread.sleep(100);
        System.out.println("t4 (WAITING): " + t4.getState());

        // 5. TIMED_WAITING (限时等)
        Thread t5 = new Thread(() -> {
            try { Thread.sleep(2000); } catch (Exception e) {}
        });
        t5.start(); Thread.sleep(100);
        System.out.println("t5 (TIMED_WAITING): " + t5.getState());

        // 6. TERMINATED (结束)
        Thread t6 = new Thread(() -> {});
        t6.start(); t6.join();
        System.out.println("t6 (TERMINATED): " + t6.getState());
    }
}