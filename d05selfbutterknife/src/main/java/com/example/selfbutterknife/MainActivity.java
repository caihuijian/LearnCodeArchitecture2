package com.example.selfbutterknife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    //3 ButterKnife属性初始化 注意View必须有id
    @BindView(R.id.tv1)
    TextView mTextView;

    @BindView(R.id.button1)
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView.setText("Activity1-butter-tv1");
        mButton.setText("Activity1-butter-button1");
    }

    @OnClick(R.id.button1)
    void buttonClick() {
        Intent intent = new Intent(MainActivity.this,Activity2.class);
        startActivity(intent);
    }
}