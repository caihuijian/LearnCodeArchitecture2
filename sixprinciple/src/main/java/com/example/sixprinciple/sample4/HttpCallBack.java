package com.example.sixprinciple.sample4;

public abstract class HttpCallBack<T>{

    // 返回可以直接操作的对象
    public abstract void onSuccess(T result);

    public abstract void onFailure(Exception e);
}
