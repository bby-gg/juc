# Day 10 学习笔记

今天学习了：
1. 手动利用 Thread 循环模拟线程池的核心原理。
2. 复习了 `synchronized` 解决并发安全问题。

遇到的问题：
- 一开始完全不懂七个参数，后来通过手写土味 Demo，彻底明白了“核心线程 -> 队列 -> 临时工 -> 拒绝”的流程。
- 目前还没学到 `ThreadPoolExecutor` 和 `ExecutorService`，后续学会后会把代码进阶。