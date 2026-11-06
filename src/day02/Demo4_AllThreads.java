package day02;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 看一个 Java 进程里到底有多少线程。
 * 会发现 main 只是其中之一，还有 GC、Finalizer 等。
 */
public class Demo4_AllThreads {

    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            try { Thread.sleep(Long.MAX_VALUE); } catch (InterruptedException e) {}
        }, "我的线程").start();

        ThreadMXBean mx = ManagementFactory.getThreadMXBean();
        System.out.println("线程总数: " + mx.getThreadCount());
        for (ThreadInfo info : mx.dumpAllThreads(false, false)) {
            System.out.println("  " + info.getThreadName() + "  ->  " + info.getThreadState());
        }
    }
}