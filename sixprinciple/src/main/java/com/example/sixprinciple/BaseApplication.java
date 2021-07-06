package com.example.sixprinciple;

import android.app.Application;

import com.example.sixprinciple.sample4.PreferencesUtil;

import org.xutils.BuildConfig;
import org.xutils.x;


public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesUtil.getInstance().init(this);
        //x.Ext.init(this);
        //x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        // HttpUtils.initHttpRequest(new OKHttpRequest());
    }
}
