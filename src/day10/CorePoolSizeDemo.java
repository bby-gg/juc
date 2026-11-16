package day10;

public class CorePoolSizeDemo {
    public static void main(String[] args) {
        // 我们不用线程池，我们自己用普通 Thread 造 2 个“核心员工”
        for (int i = 1; i <= 2; i++) {
            final int workerId = i;
            Thread worker = new Thread(() -> {
                // 员工一直循环，干 5 个任务
                for (int taskId = 1; taskId <= 5; taskId++) {
                    System.out.println("员工" + workerId + " 完成了任务" + taskId);
                    try { Thread.sleep(500); } catch (InterruptedException e) {}
                }
            });
            worker.start(); // 启动员工
        }
    }
}