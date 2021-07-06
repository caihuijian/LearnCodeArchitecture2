package com.example.sixprinciple.sample2;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sixprinciple.ConstantValue;
import com.example.sixprinciple.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Sample2.MainActivity";

    /**
     * 相比于sample1 sample2将冗余繁杂的重复参数抽取出来放在公共的get方法中
     * 并且外部只需要传递一个CallBack即可 请求和CallBack的处理都集中放在工具类中
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /********************访问网络开始*******************/
        Map<String, Object> params = new HashMap<>();
        // 特定参数
        params.put("iid", 6152551759L);
        params.put("aid", 7);

        HttpUtils.get(this, ConstantValue.UrlConstant.HOME_DISCOVERY_URL, params, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                // 失败
                Log.e(TAG, "fail " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resultJson = response.body().string();
                Log.e(TAG, "success " + resultJson);
            }
        });
        /********************访问网络结束*******************/
    }
}
