# Day09 - JUC 原子类与 CAS

本模块记录了 JUC 并发基石 **CAS** 的核心原理，以及 **原子类全家桶** 的使用与避坑指南。

## 📁 Demo 目录说明
*   `CasDemo.java`：CAS 的“慢动作”演示，对比普通变量与原子变量的竞争差异。
*   `AtomicRefDemo1.java`：原子引用基本用法（注意：比较的是**对象引用地址**）。
*   `AbaDemo.java`：复现 ABA 问题，演示 `AtomicStampedReference` 如何用版本号解决。
*   `AtomicArrayDemo.java`：原子数组操作（每个元素独立原子更新）。
*   `FieldUpdaterDemo.java`：字段更新器（不改变原有对象结构，省内存地实现原子操作）。

## 💡 核心知识点速查

**1. CAS 三大问题与解法**
*   **ABA 问题**：值被改回原样。解法：`AtomicStampedReference`（加版本号）或改用不可变对象。
*   **自旋开销**：高并发下空耗 CPU。解法：高并发统计改用 `LongAdder`（分段累加）。
*   **单变量限制**：一次只能原子更新一个变量。解法：将多字段封装为不可变对象，用 `AtomicReference` 更新。

**2. 命名规律**
*   `getAndXxx`：返回**旧值**（如 `getAndIncrement`）。
*   `xxxAndGet`：返回**新值**（如 `incrementAndGet`）。

**3. 比较语义（避坑）**
*   `AtomicInteger`：比较的是**数值**。
*   `AtomicReference`：比较的是**引用地址（==）**。千万别用 `new` 出来的对象去 CAS，必须用 `get()` 出来的引用。

**4. LongAdder vs AtomicLong**
*   **AtomicLong**：单点 CAS，高并发慢；但 `get()` 是精确瞬时值。
*   **LongAdder**：Base + Cell 分段累加，高并发极快；但 `sum()` 是非精确的最终一致性快照。