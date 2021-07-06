package com.example.sixprinciple.sample5;

import android.content.Context;

import java.util.Map;

public interface IHttpRequest {
    <T> void get(Context context, String url, Map<String, Object> params,
        final HttpCallBack<T> callback, final boolean cache);

    <T> void post(Context context, String url, Map<String, Object> params,
             final HttpCallBack<T> callback, final boolean cache);

    <T> void download(Context context, String url, Map<String, Object> params,
                  final HttpCallBack<T> callback);

    <T> void upload(Context context, String url, Map<String, Object> params,
                      final HttpCallBack<T> callback);
}
