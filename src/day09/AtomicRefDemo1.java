package day09;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicRefDemo1 {
    static class User {
        String name;
        User(String name) { this.name = name; }
        public String toString() { return "User(" + name + ")"; }
    }

    public static void main(String[] args) {
        AtomicReference<User> ref = new AtomicReference<>(new User("张三"));
        System.out.println("初始 = " + ref.get());

        User old = ref.get();
        boolean ok1 = ref.compareAndSet(old, new User("李四"));
        System.out.println("用 get() 拿到的引用 CAS = " + ok1 + "，现在 = " + ref.get());

        // 再用旧的 old 去 CAS，会失败
        boolean ok2 = ref.compareAndSet(old, new User("王五"));
        System.out.println("用过期引用再 CAS = " + ok2 + "，现在 = " + ref.get());

        // 原子替换并返回旧值
        User prev = ref.getAndSet(new User("赵六"));
        System.out.println("getAndSet 返回旧值 = " + prev + "，现在 = " + ref.get());
    }
}