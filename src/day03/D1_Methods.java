package day03;

public class D1_Methods {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("t1 睡觉 2 秒...");
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            System.out.println("t1 醒了");
        }, "t1");

        t1.start();
        System.out.println("main 执行 join，死等 t1 结束...");
        t1.join(); // main 死等 t1 结束
        System.out.println("main 结束");
    }
}