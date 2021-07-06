package com.example.sixprinciple.sample3;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sixprinciple.ConstantValue;
import com.example.sixprinciple.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Sample3.MainActivity";

    /**
     * Sample3 比起Sample2
     * 1.多考虑了缓存问题 对比缓存数据是否和cloud最新数据一致
     * 先显示缓存数据
     * 如果一致 直接返回
     * 如果不一致 更新界面 将新数据缓存
     * 2.使用了泛型类型的HttpCallBack
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

        HttpUtils.get(this, ConstantValue.UrlConstant.HOME_DISCOVERY_URL,
                params,new HttpCallBack<DiscoverListResult>() {
                    @Override
                    public void onSuccess(DiscoverListResult result) {
                        if (result.isOK()) {
                            // 没有列表数据的情况, 打印 Toast 或者做一些其他处理
                            Log.e(TAG, "success is OK "+result);
                        } else {
                            // 有数据列表的情况，显示列表
                            // showListData(result);
                            Log.e(TAG, "success is NG "+result);
                        }
                    }
                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "fail " + e.getMessage());
                    }
                },true);
        /********************访问网络结束*******************/
    }
}
