package com.example.sixprinciple.sample5;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.sixprinciple.Utils;
import com.google.gson.Gson;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class XUtilsRequest implements IHttpRequest {
    private static final String TAG = "Sample5.XUtilsRequest";
    private ICache mHttpCache;

    public XUtilsRequest() {
        mHttpCache = new SPHttpCache();
    }

    // 参数还是很多
    public <T> void get(Context context, String url, Map<String, Object> params,
                        final HttpCallBack<T> callback, final boolean cache) {
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

        final String jointUrl = Utils.jointParams(url, params);
        // 缓存问题
        final String cacheJson = mHttpCache.getCache(jointUrl);
        // 写一大堆处理逻辑 ，内存怎么扩展等等
        if (cache && !TextUtils.isEmpty(cacheJson)) {
            Gson gson = new Gson();
            // data:{"name","darren"}   data:"请求失败"
            T objResult = (T) gson.fromJson(cacheJson,
                    Utils.analysisClazzInfo(callback));
            callback.onSuccess(objResult);
        }

        Log.e(TAG, "request: mUrl " + jointUrl);
        RequestParams requestParams = new RequestParams();
        requestParams.setUri(jointUrl);
        x.http().get(requestParams, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "onSuccess: " + result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, "onSuccess: " + ex.getMessage());
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e(TAG, "onCancelled: " + cex.getMessage());
                cex.printStackTrace();
            }

            @Override
            public void onFinished() {
                Log.e(TAG, "onFinished: ");
            }
        });
    }

    @Override
    public <T> void post(Context context, String url, Map<String, Object> params, HttpCallBack<T> callback, boolean cache) {

    }

    @Override
    public <T> void download(Context context, String url, Map<String, Object> params, HttpCallBack<T> callback) {

    }

    @Override
    public <T> void upload(Context context, String url, Map<String, Object> params, HttpCallBack<T> callback) {

    }
}
