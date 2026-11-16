package day10;

public class HandlerDemo {
    public static void main(String[] args) {
        // 假设我们规定：最多只能处理 2 个任务
        int maxCapacity = 2;

        for (int i = 1; i <= 5; i++) {
            if (i > maxCapacity) {
                // 这就是“拒绝策略”！处理不了了，直接报错或提示
                System.out.println("❌ 任务" + i + " 被拒绝了，系统繁忙！");
            } else {
                System.out.println("✅ 任务" + i + " 被接受了，开始排队或执行。");
            }
        }
    }
}