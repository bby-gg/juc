# Day13 - 定时任务线程池与异常处理笔记

## 1. ScheduledThreadPoolExecutor (定时任务)
- **创建方式**：`Executors.newScheduledThreadPool(n)`
- **核心方法**：
    - `schedule(Runnable, delay, unit)`：延迟执行一次。
    - `scheduleAtFixedRate(Runnable, initialDelay, period, unit)`：延迟 initialDelay 后首次执行，之后每隔 period 执行一次。
- **⚠️ 大坑**：如果在周期性任务中抛出了未捕获的异常，**该任务后续的周期执行会直接终止**（但线程池本身不会挂）。

## 2. 正确处理线程池异常
- **execute 提交**：如果任务抛出异常，异常会直接输出到控制台（打印堆栈），但线程池仍可继续工作。
- **submit 提交**：如果任务抛出异常，异常会被静默“吞掉”并封装在 `Future` 中。必须调用 `future.get()` 才能捕获，抛出的是 `ExecutionException`，需要用 `getCause()` 拿真正的异常。

## 3. 定时任务实际应用场景
- 心跳检测（定时 ping 服务，检测存活状态）。
- 超时监控（定期扫描订单是否超时未支付）。
- 定时清理（清空临时文件、过期缓存等）。