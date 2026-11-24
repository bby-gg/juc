# Semaphore 学习笔记

> Java 并发包中的信号量，用于控制同时访问某个资源的线程数量。包名：`day16`

---

## 一、Semaphore 是什么？

`Semaphore`（信号量）是一个计数器，用来**控制同时访问某个资源的线程数量**。

你可以把它理解成**停车场**：
- 有 N 个车位（permits/许可证）
- 车（线程）来了先拿一个车位
- 没车位就在门口等
- 开走时把车位还回去

**核心方法：**

| 方法 | 说明 |
|------|------|
| `acquire()` | 获取一个许可，没许可就阻塞等待 |
| `acquire(int n)` | 获取 n 个许可 |
| `release()` | 释放一个许可 |
| `release(int n)` | 释放 n 个许可 |
| `tryAcquire()` | 尝试获取，立即返回 true/false |
| `tryAcquire(long timeout, TimeUnit unit)` | 超时获取 |
| `availablePermits()` | 当前可用许可数 |

---

## 二、构造方法

```java
// 公平锁：先到先得
Semaphore semaphore = new Semaphore(3, true);

// 非公平锁（默认）：允许插队，吞吐量更高
Semaphore semaphore = new Semaphore(3, false);
Semaphore semaphore = new Semaphore(3);