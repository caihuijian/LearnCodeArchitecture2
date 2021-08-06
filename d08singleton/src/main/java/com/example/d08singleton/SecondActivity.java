package com.example.d08singleton;

import android.os.Bundle;
import android.view.View;

import com.example.d08singleton.manager.ActivityManager;

/**
 * Created by hjcai on 2021/8/4.
 */
public class SecondActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void jump(View view) {
        ActivityManager.getInstance().finish(this);
        ActivityManager.getInstance().finish(MainActivity.class);
    }
}
