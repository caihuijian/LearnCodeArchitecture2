package com.example.d02aop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.d02aop.ioc.CheckNetThenClick;
import com.example.d02aop.ioc.ViewUtils;

public class MainActivity extends AppCompatActivity {
    //  判空? 找一个更容易理解AOP的文章

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.injectActivity(MainActivity.this);
    }

    @CheckNetThenClick(R.id.button1)
    public void network1() {
        Utils.isNetworkAvailable(MainActivity.this);
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
}