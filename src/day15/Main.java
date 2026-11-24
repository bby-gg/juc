package day15;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Point p = new Point();

        // 写线程：一直移动
        Thread writer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                p.move(1, 1);
                sleep(50);
            }
        });

        // 读线程：一直算距离
        Thread reader = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("距离 = " + p.distance());
                sleep(50);
            }
        });

        writer.start();
        reader.start();
        writer.join();
        reader.join();
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { }
    }
}