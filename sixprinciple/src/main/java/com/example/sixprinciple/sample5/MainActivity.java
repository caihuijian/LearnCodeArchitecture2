package com.example.sixprinciple.sample5;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sixprinciple.ConstantValue;
import com.example.sixprinciple.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Sample5.MainActivity";

    /**
     * Sample5 比起Sample4
     * 1.面向接口编程 HttpUtils中的IHttpRequest是一个接口 可以被赋值为任意接口实现者 如果需要切换引擎 十分方便
     * 同时提供了XUtils的请求 使用XUtils访问网络 可以灵活切换使用的网络引擎
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /********************访问网络开始*******************/
        HttpUtils.with(this).cache(true).get()
                //.httpRequest(new XUtilsRequest())//中途切换引擎
                .param("iid", 6152551759L).param("aid", 7).url(ConstantValue.UrlConstant.HOME_DISCOVERY_URL).request(
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
