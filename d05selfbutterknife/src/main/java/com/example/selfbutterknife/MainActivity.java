package com.example.selfbutterknife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    // 3.ButterKnife属性初始化 注意View必须有id
    @BindView(R.id.tv1)
    TextView mTextView;

    @BindView(R.id.button1)
    Button mButton;
    Unbinder unbinder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //2 初始化ButterKnife
        unbinder = ButterKnife.bind(this);
        //4 ButterKnife属性使用
        mTextView.setText("Activity1-butter-tv1");
        mButton.setText("Activity1-butter-button1");
    }

    //5 ButterKnife Event使用
    @OnClick(R.id.button1)
    void buttonClick() {
        Intent intent = new Intent(MainActivity.this,Activity2.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 6.调用unbind取消ButterKnife的注入
        unbinder.unbind();
    }
}