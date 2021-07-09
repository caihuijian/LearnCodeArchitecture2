package com.example.d02aop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.d02aop.aop.AOPCheckNet;
import com.example.d02aop.ioc.CheckNetThenClick;
import com.example.d02aop.ioc.ViewUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.injectActivity(MainActivity.this);
    }

    @CheckNetThenClick(R.id.button1)
    public void network1() {
        Toast.makeText(MainActivity.this,"访问网络1！",Toast.LENGTH_SHORT).show();
    }

    @CheckNetThenClick(R.id.button2)
    public void network2() {
        Toast.makeText(MainActivity.this,"访问网络2！",Toast.LENGTH_SHORT).show();
    }

    @CheckNetThenClick(R.id.button3)
    public void network3() {
        Toast.makeText(MainActivity.this,"访问网络3！",Toast.LENGTH_SHORT).show();
    }

    @AOPCheckNet
    public void click1(View view) {
        Toast.makeText(MainActivity.this,"click1 访问网络1！",Toast.LENGTH_SHORT).show();
    }

    @AOPCheckNet
    public void click2(View view) {
        Toast.makeText(MainActivity.this,"click2 访问网络2！",Toast.LENGTH_SHORT).show();
    }

    @AOPCheckNet
    public void click3(View view) {
        Toast.makeText(MainActivity.this,"click3 访问网络3！",Toast.LENGTH_SHORT).show();
    }
}