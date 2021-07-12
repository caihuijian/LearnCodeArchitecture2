package com.example.d02aop.aspectjtest;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by hjcai on 2021/7/9.
 */
@Aspect
public class AspectJTest {
    private static final String TAG = "AspectJTest";

    // 1. @Before 插入点/切点 这里指 调用函数之前
    // 2. execution 处理Join Point的类型，例如call、execution
    // 3. * com.example.d02aop.MainActivity.before(..)
    // 第一个*表示返回值，*表示返回值为任意类型
    // 4. com.example.d02aop.MainActivity.before 是典型的包名路径，其中可以包含 * 来进行通配 还可以通过&&、||、!来进行条件组合
    // 比如如果写成com.example.d02aop.MainActivity.* 那么MainActivity下所有的方法调用前都会调用onMethodBefore
    // 注意 如果AspectJTest与目标方法同目录 可以省略包名写成 @Before("execution(* before(..))")
    // (..)  ()代表这个方法的参数，你可以指定类型，例如android.os.Bundle，或者(..)这样来代表任意类型、任意个数的参数
    // public void onMethodBefore(JoinPoint joinPoint) throws Throwable：实际切入的代码
    @Before("execution(* com.example.d02aop.MainActivity.before(..))")
    public void onMethodBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.d(TAG, "printLog for before");
    }

    @After("execution(* com.example.d02aop.MainActivity.after(..))")
    public void onMethodAfter(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.d(TAG, "printLog for before");
    }

    @Around("execution(* com.example.d02aop.MainActivity.around(..))")
    public Object onMethodAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.d(TAG, "printLog for around");
        // 1.判断有没有网络
        Object object = joinPoint.getThis();// View Activity Fragment:getThis() 当前切点方法所在的类 是activity或者fragment或者view
        Context context = getContext(object);// 获取上下文 用于判断网络状态
        if (context != null) {
            if (!isNetworkAvailable(context)) {
                // 2.没有网络不要往下执行
                Toast.makeText(context, "请检查您的网络", Toast.LENGTH_SHORT).show();
                return null;// 阻断执行
            }
        }
        return joinPoint.proceed();
    }

    /**
     * 通过对象获取上下文
     */
    private Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;
    }

    /**
     * 检查当前网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean connected = false;
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        StringBuilder sb = new StringBuilder();
        if (connectivityManager != null) {
            // 获取NetworkInfo对象
            Network[] networks = connectivityManager.getAllNetworks();
            //用于存放网络连接信息

            for (int i = 0; i < networks.length; i++) {
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(networks[i]);
                sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
                if (networkInfo.isConnected()) {
                    connected = true;
                }
            }
        }
        Log.d(TAG, "isNetworkAvailable: " + sb);
        Log.d(TAG, "connected: " + connected);
        return connected;
    }

}
