package com.example.sixprinciple.sample3;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.sixprinciple.Utils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {
    private static String TAG = "Sample3";

    private HttpUtils() {
    }

    public static <T> void get(Context context, String url, Map<String, Object> params, final HttpCallBack<T> callback, final boolean cache) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        // 公共参数
        params.put("app_name", "joke_essay");
        params.put("version_name", "5.7.0");
        params.put("ac", "wifi");
        params.put("device_id", "30036118478");
        params.put("device_brand", "Xiaomi");
        params.put("update_version_code", "5701");
        params.put("manifest_version_code", "570");
        params.put("longitude", "113.000366");
        params.put("latitude", "28.171377");
        params.put("device_platform", "android");

        final String jointUrl = Utils.jointParams(url, params);  //打印

        Log.e(TAG, " url: "+jointUrl);
        // 缓存问题
        // 缓存写到  SP 里面, 考虑多级缓存(比如说 最经常使用的放在内存中,2天内使用的数据放在数据库，其余放在文件中 )
        final String cacheJson = (String) PreferencesUtil.getInstance().getParam(jointUrl, "");
        // 写一大堆多级缓存处理逻辑,内存怎么扩展等等
        // 这里只考虑了一级缓存

        if (cache && !TextUtils.isEmpty(cacheJson)) {
            // 从缓存中取得数据
            Gson gson = new Gson();
            // data:{"name","darren"}   data:"请求失败"
            T objResult = (T) gson.fromJson(cacheJson,
                    Utils.analysisClazzInfo(callback));
            callback.onSuccess(objResult);
        }

        // 同时并行得实时查询cloud数据
        Request.Builder requestBuilder = new Request.Builder().url(jointUrl).tag(context);
        //可以省略，默认是GET请求
        Request request = requestBuilder.build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                // 失败
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resultJson = response.body().string();

                Log.e(TAG,resultJson.equals(cacheJson)+"");
                if (cache && resultJson.equals(cacheJson)) {
                    // 缓存数据与cloud数据相同 直接返回
                    return;
                }
                // 1.JSON解析转换
                // 2.显示列表数据
                // 3.缓存数据
                Gson gson = new Gson();
                // data:{"name","darren"}   data:"请求失败"
                T objResult = (T) gson.fromJson(resultJson,
                        Utils.analysisClazzInfo(callback));
                callback.onSuccess(objResult);

                if (cache) {// 缓存数据
                    PreferencesUtil.getInstance().saveParam(jointUrl, resultJson);
                }
            }
        });
    }
}
