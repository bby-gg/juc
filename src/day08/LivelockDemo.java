public class LivelockDemo {
    static volatile boolean flag = true;

    public static void main(String[] args) {
        new Thread(() -> {
            while (flag) {
                System.out.println("T1 主动让路，重新尝试");
                sleep(10);
            }
            System.out.println("T1 完成");
        }, "T1").start();

        new Thread(() -> {
            while (flag) {
                System.out.println("T2 主动让路，重新尝试");
                sleep(10);
            }
        }, "T2").start();

        sleep(500);
        flag = false; // 外部干预才结束
    }
    static void sleep(long ms){ try{Thread.sleep(ms);}catch(Exception e){} }
}