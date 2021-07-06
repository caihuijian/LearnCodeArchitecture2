package com.example.sixprinciple.sample1;

import android.os.Bundle;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

import com.example.sixprinciple.ConstantValue;
import com.example.sixprinciple.R;
import com.example.sixprinciple.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Sample1.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /********************访问网络开始*******************/
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Map<String, Object> params = new HashMap<>();
        // 特定参数
        params.put("iid", 6152551759L);
        params.put("aid", 7);
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

        final String jointUrl = Utils.jointParams(ConstantValue.UrlConstant.HOME_DISCOVERY_URL, params);  //打印
        Log.e(TAG, "url:" + jointUrl);

        Request.Builder requestBuilder = new Request.Builder().url(jointUrl).tag(this);
        //可以省略，默认是GET请求
        Request request = requestBuilder.build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                // 失败
                Log.e(TAG, "fail " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 内涵段子上面的url请求的网络服务其实已经关闭
                final String resultJson = response.body().string();
                Log.e(TAG, "success " + resultJson);
            }
        });
        /********************访问网络结束*******************/
    }
}
