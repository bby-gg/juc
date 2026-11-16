package day10;

public class KeepAliveDemo {
    public static void main(String[] args) throws InterruptedException {
        // 我们造一个临时工线程
        Thread tempWorker = new Thread(() -> {
            System.out.println("临时工：我来干活了！");
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            System.out.println("临时工：活干完了，没人管我，我先待着...");
        });

        tempWorker.start();

        // 主线程说：你闲的时间太长了，把你开除（打断你）！
        Thread.sleep(3000); // 等 3 秒（模拟 keepAliveTime）
        System.out.println("老板：过了3秒了，临时工你下班吧！");
        tempWorker.interrupt(); // 强制打断（销毁临时工）
    }
}