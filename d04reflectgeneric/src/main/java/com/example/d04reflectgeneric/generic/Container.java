package com.example.d04reflectgeneric.generic;

/**
 * Created by hjcai on 2021/7/15.
 * 类泛型学习
 */
public class Container<T> {//此处的<T>是泛型的声明
    // 创建只能存储固定类型的数组
    private Object[] items = new Object[10];

    public void add(T t) {
        items[0] = t;
    }

    public T get(int index) {
        return (T) items[index];
    }
}
