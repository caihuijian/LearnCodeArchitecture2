package com.example.d02aop.aop;

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
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by hjcai on 2021/7/8.
 */
@Aspect
public class SectionAspect {
    private static final String TAG = "SectionAspect";

    /**
     * 找到处理的切点
     * * *(..)  可以处理所有的方法
     */
    @Pointcut("execution(@com.example.d02aop.aop.AOPCheckNet * *(..))")
    public void checkNetBehavior() {

    }

    /**
     * 处理切面
     */
    @Around("checkNetBehavior()")
    public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e(TAG, "checkNet");
        // AOP用于做埋点  可以做日志上传  权限检测 网络检测等
        // 1.获取 CheckNet 注解 调用Java 方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AOPCheckNet checkNet = signature.getMethod().getAnnotation(AOPCheckNet.class);
        if (checkNet != null) {
            // 2.判断有没有网络
            Object object = joinPoint.getThis();// View Activity Fragment:getThis() 当前切点方法所在的类 是activity或者fragment或者view
            Context context = getContext(object);
            if (context != null) {
                if (!isNetworkAvailable(context)) {
                    // 3.没有网络不要往下执行
                    Toast.makeText(context, "请检查您的网络", Toast.LENGTH_SHORT).show();
                    return null;
                }
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
        Log.e(TAG, "isNetworkAvailable: " + sb);
        Log.e(TAG, "connected: " + connected);
        return connected;
    }
}
