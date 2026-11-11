public class DeadlockDemo {
    static final Object A = new Object();
    static final Object B = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (A) {
                System.out.println("T1 拿到 A，等 B");
                sleep(100);
                synchronized (B) { System.out.println("T1 拿到 B"); }
            }
        }, "T1").start();

        new Thread(() -> {
            synchronized (B) {
                System.out.println("T2 拿到 B，等 A");
                sleep(100);
                synchronized (A) { System.out.println("T2 拿到 A"); }
            }
        }, "T2").start();
    }
    static void sleep(long ms){ try{Thread.sleep(ms);}catch(Exception e){} }
}