package day16;

import java.util.concurrent.Semaphore;

/**
 * 停车场：3 个车位，6 辆车抢
 */
public class ParkingLotDemo {

    public static void main(String[] args) {
        // 3 个车位（公平锁，先到先得）
        Semaphore parkingLot = new Semaphore(3, true);

        // 6 辆车
        for (int i = 1; i <= 6; i++) {
            final int carId = i;
            new Thread(() -> {
                try {
                    System.out.printf("[车%d] 到达停车场，等待车位...%n", carId);
                    parkingLot.acquire();   // 拿许可（没车位就等）
                    System.out.printf("[车%d] 停好车，剩余车位=%d%n",
                            carId, parkingLot.availablePermits());
                    Thread.sleep(2000);      // 停车 2 秒
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    parkingLot.release();   // 释放许可（开走）
                    System.out.printf("[车%d] 离开停车场%n", carId);
                }
            }, "Car-" + i).start();
        }
    }
}