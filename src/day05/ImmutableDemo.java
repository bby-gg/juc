package day05;

/**
 * 演示：不可变对象天然线程安全
 *
 * String、Integer 创建后不能改，"修改"其实是生成新对象，原对象不变。
 * 多个线程同时读同一个不可变对象，不会出问题。
 *
 * 注意：n + 1 这种裸表达式不能单独成句，要赋值或使用结果。
 */
public class ImmutableDemo {

    public static void main(String[] args) throws InterruptedException {
        String s = "hello";
        Integer n = 100;

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                String s2 = s.concat("x"); // 生成新字符串，s 不变
                Integer n2 = n + 1;        // 生成新 Integer，n 不变
                if (s2 == null || n2 == null) {
                    throw new IllegalStateException(); // 防止结果被优化掉，实际不会触发
                }
            }
        }, "线程1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                String s3 = s.concat("y");
                Integer n3 = n + 2;
                if (s3 == null || n3 == null) {
                    throw new IllegalStateException();
                }
            }
        }, "线程2");

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("s = " + s); // 始终 hello
        System.out.println("n = " + n); // 始终 100
    }
}