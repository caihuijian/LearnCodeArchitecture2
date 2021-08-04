package com.example.d08singleton.singleton.container;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hjcai on 2021/8/4.
 * 容器管理 系统的服务就是用的这种 好处是可以统一管理各种单例 不过这种方式的缺点是和饿汉式一样 实例初始化太早
 * 该思想参考自SystemServiceRegistry SYSTEM_SERVICE_FETCHERS 可以查看源码看看实现
 * 该方式没有不算典型意义的单例 只是有这种思想 它没有私有化构造方法 也没有在类中提供public static getInstance的方法（这里只Android 原生代码）
 */
public class Singleton {
    private static Map<String, Object> mSingleMap = new HashMap<>();

    static {
        mSingleMap.put("activity_manager", new Singleton());
    }

    private Singleton() {

    }

    public static Object getService(String serviceName) {
        return mSingleMap.get(serviceName);
    }
}
