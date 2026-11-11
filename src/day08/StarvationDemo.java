public class StarvationDemo {
    public static void main(String[] args) {
        // 一个"贪吃"线程，反复抢锁不释放太久
        new Thread(() -> {
            while (true) {
                synchronized (StarvationDemo.class) {
                    System.out.println("贪吃线程又抢到了");
                    //sleep(1);
                }
            }
        }, "Greedy").start();

        // 一个"老实"线程，迟迟拿不到锁
        new Thread(() -> {
            synchronized (StarvationDemo.class) {
                System.out.println("老实线程终于拿到锁！");
            }
        }, "Honest").start();
    }
    static void sleep(long ms){ try{Thread.sleep(ms);}catch(Exception e){} }
}