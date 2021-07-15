package com.example.d04reflectgeneric.generic;

/**
 * Created by hjcai on 2021/7/15.
 * <p>
 * 方法泛型学习
 */
public class MethodGeneric/*如果把<T>泛型申明移到这里就是类的泛型了 T可以在类内部使用 方法上的<T>的声明就可以删除了*/ {
    // <T>表示在方法中声明泛型
    public <T> void onSuccess(T result) {

    }
}
