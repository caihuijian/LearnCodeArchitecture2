package com.example.sixprinciple.sample4;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sixprinciple.ConstantValue;
import com.example.sixprinciple.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Sample4.MainActivity";

    /**
     * Sample4 比起Sample3
     * 1.HttpUtils使用了builder设计模式
     * 2.将请求单独新建为OKHttpRequest 与 HttpUtils分离 结构更清晰
     * 同时 缓存部分也新建为一个类 与 HttpUtils分离
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /********************访问网络开始*******************/
        HttpUtils.with(this).cache(true).get().param("iid", 6152551759L).param("aid", 7).url(ConstantValue.UrlConstant.HOME_DISCOVERY_URL).request(
                new HttpCallBack<DiscoverListResult>() {
                    @Override
                    public void onSuccess(DiscoverListResult result) {
                        if (result.isOK()) {
                            // 没有列表数据的情况, 打印 Toast 或者做一些其他处理
                        } else {
                            // 有数据列表的情况，显示列表
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
        /********************访问网络结束*******************/
    }
}
