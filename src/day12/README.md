# Day12 - ExecutorService 提交任务的 6 个方法

## 核心区分
- **Runnable vs Callable**：前者无返回值，后者有返回值。
- **execute vs submit**：`execute` 只能跑 Runnable；`submit` 既能跑 Runnable 也能跑 Callable，且返回 `Future`。

## 方法速记

1. **execute(Runnable)**
    - 执行无返回值的任务。

2. **submit(Callable<T>)**
    - 提交任务，返回 `Future`，可以通过 `future.get()` 阻塞获取结果。

3. **invokeAll(Collection<Callable>)**
    - 提交所有任务，**等全部执行完**，返回所有结果的 `Future` 列表。
    - *(带 timeout 的重载：如果超时，没跑完的任务会被取消并抛异常)*。

4. **invokeAny(Collection<Callable>)**
    - 提交所有任务，**谁先成功完成，就返回谁的结果**，并自动取消其他任务。
    - 适合“多路并发查找，只要一个结果”的场景（如：同时请求多个镜像源，用最快的那个）。
    - *(带 timeout 的重载：如果时间内没任何任务完成，抛 TimeoutException)*。