package com.example.d02aop;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by hjcai on 2021/7/8.
 */
public class Utils {
    private static final String TAG = "Utils";

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
