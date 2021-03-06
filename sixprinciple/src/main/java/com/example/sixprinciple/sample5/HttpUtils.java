package com.example.sixprinciple.sample5;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
    // 重点 面向接口编程 使用IHttpRequest接口对象 而不是实现类
    // 可以中途切换Http引擎 比如换成Xutils
    private IHttpRequest mHttpRequest;
    //初始化Http引擎
    private static IHttpRequest mInitHttpRequest;
    private final int TYPE_POST = 0x0011, TYPE_GET = 0x0022;
    private int mType = TYPE_GET;
    // 用于临时存储参数
    private Map<String, Object> mParams;
    private String mUrl;
    private boolean mCache = false;
    private Context mContext;

    private static String TAG = "Sample5";

    // 使用了builder设计模式
    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    private HttpUtils(Context context) {
        mHttpRequest = new OKHttpRequest();
        mParams = new HashMap<>();
        this.mContext = context;
    }

    // 提供给外部初始化Http引擎的接口
    public static void initHttpRequest(IHttpRequest httpRequest) {
        mInitHttpRequest = httpRequest;
    }

    // 重点：提供给外部用于切换引擎的接口
    public HttpUtils httpRequest(IHttpRequest httpRequest){
        mHttpRequest = httpRequest;
        return this;
    }

    public HttpUtils param(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    public HttpUtils url(String url) {
        mUrl = url;
        return this;
    }

    public HttpUtils cache(boolean cache) {
        mCache = cache;
        return this;
    }

    public <T> void request() {
        request(null);
    }

    public <T> void request(final HttpCallBack<T> callback) {
        if(mHttpRequest == null){
            mHttpRequest = mInitHttpRequest;
        }
        // 异常判断
        if (mContext == null) {
            Log.e(TAG, "request: mContext is null,请调用with方法！");
        }
        mHttpRequest.get(mContext, mUrl, mParams, callback, mCache);
    }

    public HttpUtils get() {
        mType = TYPE_GET;
        return this;
    }

    // 外部直接调用该方法 传递参数太多会觉得很难用 应该提供较少的参数的方法 外部不需要知道所有参数的意义
    // 真正想用的参数自己调用方法设定即可 没有传递的参数可以使用默认值
    /*public static <T> void get(Context context, String url, Map<String, Object> params, final HttpCallBack<T> callback, final boolean cache) {
    }*/
}
