package com.example.d08singleton;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.d08singleton.manager.ActivityManager;

/**
 * Created by hjcai on 2021/8/4.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().attach(this);
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().detach(this);
        super.onDestroy();
    }
}
