package day03;

public class D2_Interrupt {
    public static void main(String[] args) throws InterruptedException {
        // 场景1：打断 sleep
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("t1 被打断，抛异常，标记被清除: " + Thread.currentThread().isInterrupted());
            }
        });
        t1.start();
        Thread.sleep(500);
        t1.interrupt(); // 瞬间叫醒 t1

        // 场景2：打断正常干活
        Thread t2 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) { // 自己检查标记
                    System.out.println("t2 发现标记为 true，自行退出");
                    break;
                }
            }
        });
        t2.start();
        Thread.sleep(500);
        t2.interrupt(); // 仅把标记设为 true，t2 不会立刻停
    }
}