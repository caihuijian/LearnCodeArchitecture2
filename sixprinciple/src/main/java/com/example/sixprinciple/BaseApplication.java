package com.example.sixprinciple;

import android.app.Application;

//import com.example.sixprinciple.sample4.PreferencesUtil;
import com.example.sixprinciple.sample5.HttpUtils;
import com.example.sixprinciple.sample5.OKHttpRequest;
import com.example.sixprinciple.sample5.PreferencesUtil;

import org.xutils.BuildConfig;
import org.xutils.x;


public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 测试不同的demo时记得切换导包
        PreferencesUtil.getInstance().init(this);
        // 初始化xUtils
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        HttpUtils.initHttpRequest(new OKHttpRequest());
    }
}
