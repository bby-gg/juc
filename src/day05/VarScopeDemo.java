package day05;

/**
 * 演示：成员变量和静态变量的共享范围不同
 *
 * count：成员变量，每个对象一份，只有多线程操作同一个对象才共享
 * staticCount：静态变量，全类一份，所有线程共享
 */
public class VarScopeDemo {

    int count = 0;              // 成员变量
    static int staticCount = 0; // 静态变量

    public void addMember() {
        count++;
    }

    public void addStatic() {
        staticCount++;
    }

    public static void main(String[] args) throws InterruptedException {
        VarScopeDemo demo = new VarScopeDemo(); // 只创建一个对象

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                demo.addMember();
                demo.addStatic();
            }
        }, "线程1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                demo.addMember();
                demo.addStatic();
            }
        }, "线程2");

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("成员变量 count = " + demo.count + "（预期 10000）");
        System.out.println("静态变量 staticCount = " + staticCount + "（预期 10000）");
    }
}