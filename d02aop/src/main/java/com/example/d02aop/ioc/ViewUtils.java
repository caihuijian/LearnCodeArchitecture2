package com.example.d02aop.ioc;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.d02aop.Utils;

import java.lang.reflect.Method;

/**
 * Created by hjcai on 2021/2/5.
 */
public class ViewUtils {
    private static final String TAG = "ViewUtils";

    //Activity绑定
    public static void injectActivity(Activity activity) {
        injectActivity(new ViewFinder(activity), activity);
    }

    private static void injectActivity(ViewFinder finder, Object object) {
        injectActivityEvent(finder, object);
    }

    private static void injectActivityEvent(ViewFinder finder, Object object) {
        //1 反射 获取class里面所有属性
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();//获取Activity的所有方法包括私有和共有
        for (Method method : methods) {
            //2 遍历fields 找到添加了注解CheckNetThenClick的method
            CheckNetThenClick check = method.getAnnotation(CheckNetThenClick.class);
            if (check != null) {
                //3 获取注解里面的id值
                int viewId = check.value();
                View view = finder.findViewById(viewId);//相当于调用Activity.findViewById
                if (view != null) {
                    // 4. 给每个view设置点击事件
                    view.setOnClickListener(new DeclaredOnClickListener(method, object));
                }
            }
        }
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {
        // mObject是当前activity
        private Object mObject;
        // mMethod是添加了CheckNetThenClick的方法
        private Method mMethod;

        public DeclaredOnClickListener(Method method, Object object) {
            this.mObject = object;
            this.mMethod = method;
        }

        @Override
        public void onClick(View v) {
            try {
                // 所有方法都可以 包括私有共有
                mMethod.setAccessible(true);
                // 反射执行方法
                // 当View被点击时执行
                if (!Utils.isNetworkAvailable(v.getContext())){
                    Toast.makeText(v.getContext(),"没有网络！",Toast.LENGTH_SHORT).show();
                    // 阻断执行
                    return;
                }
                mMethod.invoke(mObject, v);//通过反射 调用mObject的声明了CheckNetThenClick注解了的方法 参数为v
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    mMethod.invoke(mObject, null);
                    Log.e(TAG, "onClick: xxxx ");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
