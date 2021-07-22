package com.example.self_butterknife;

import android.app.Activity;

import java.lang.reflect.Constructor;

/**
 * Created by hjcai on 2021/7/19.
 */
public class ButterKnife {
    public static Unbinder bind(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException(" activity should not be null");
        }
        try {
            Class<? extends Unbinder> bindClassName = (Class<? extends Unbinder>)
                    Class.forName(activity.getClass().getName() + "_ViewBinding");
            // 构造函数
            // 通过反射调用 类似MainActivity_ViewBinding bind = new MainActivity_ViewBinding(this); 的构造方法
            Constructor<? extends Unbinder> bindConstructor = bindClassName.getDeclaredConstructor(activity.getClass());
            // 返回 Unbinder
            return bindConstructor.newInstance(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Unbinder.EMPTY;
    }
}
