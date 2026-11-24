# StampedLock 学习笔记

> Java 8 高性能读写锁，适合读多写少场景。包名：`day15`

---

## 一、StampedLock 是什么？

`StampedLock` 是 Java 8 提供的锁，比 `ReadWriteLock` 更快，适合**读多写少**的场景。

它有三种模式：

| 模式 | 方法 | 特点 |
|------|------|------|
| **写锁** | `writeLock()` | 独占，别人不能读也不能写 |
| **悲观读锁** | `readLock()` | 共享，多个读可以同时进 |
| **乐观读** | `tryOptimisticRead()` | 不加锁，读完验证一下 |

**核心特点：**
- 每次加锁会返回一个 `long` 类型的 **stamp（版本号）**
- 解锁时必须把 stamp 传回去
- **不可重入**（同一个线程不能重复加同一把锁，会死锁）
- **没有 Condition**（不能用 await/signal）

---

## 二、三种锁的用法

### 1. 写锁（独占）

```java
long stamp = lock.writeLock();
try {
    // 修改数据
    count++;
} finally {
    lock.unlockWrite(stamp);   // 必须传 stamp
}