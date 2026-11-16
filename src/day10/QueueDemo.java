package day10;

public class QueueDemo {
    public static void main(String[] args) {
        // 这是一个传送带（数组），最多放 3 个任务
        String[] queue = new String[3];

        // 模拟把任务放进传送带
        for (int i = 1; i <= 3; i++) {
            queue[i - 1] = "任务" + i;
            System.out.println(queue[i - 1] + " 进入了队列排队");
        }

        // 核心员工从队列里拿任务
        System.out.println("员工开始从队列拿任务干活...");
        for (int i = 0; i < queue.length; i++) {
            System.out.println("员工拿到了：" + queue[i] + " 并执行");
        }
    }
}