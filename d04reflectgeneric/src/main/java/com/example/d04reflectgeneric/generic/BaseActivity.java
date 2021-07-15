package com.example.d04reflectgeneric.generic;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by hjcai on 2021/7/15.
 * 泛型上限 泛型下限 学习
 */
public class BaseActivity extends AppCompatActivity {

    // 泛型上限 传入的参数必须是MainActivity及其子类
    // ? 代表任意的意思
    public void startActivity(Class<? extends BaseActivity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    // 泛型下限 传入的参数必须是MainActivity及其父类
    public void startActivity1(Class<? super BaseActivity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
