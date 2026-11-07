package day07;

/**
 * 演示：wait 必须在 synchronized 块内调用
 *
 * 不在 synchronized 内调用 wait，会抛 IllegalMonitorStateException。
 * 运行后能看到异常，证明 wait 依赖对象的 Monitor。
 */
public class WaitMustInSyncDemo {
    public static void main(String[] args) {
        Object lock = new Object();
        try {
            lock.wait();   // 没有 synchronized，直接抛异常
        } catch (IllegalMonitorStateException e) {
            System.out.println("抛异常：IllegalMonitorStateException（wait 不在 synchronized 内）");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}