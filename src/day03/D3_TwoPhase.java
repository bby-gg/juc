package day03;

public class D3_TwoPhase {
    static boolean stop = false; // 停止标记

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            while (true) {
                if (stop) { // 阶段二：自己检查标记
                    System.out.println("做收尾工作，退出...");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    System.out.println("干活中...");
                } catch (InterruptedException e) {
                    System.out.println("睡觉被打断，准备退出");
                }
            }
        });
        t.start();

        Thread.sleep(2500); // 让它跑一会
        stop = true;        // 阶段一：主线程下达停止指令
        t.interrupt();      // 把它从 sleep 中叫醒
    }
}