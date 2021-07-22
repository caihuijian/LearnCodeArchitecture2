package com.example.client;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.self_butterknife.ButterKnife;
import com.example.self_butterknife.Unbinder;
import com.example.self_butterknife_annotations.BindView;
import com.example.self_butterknife_client.MainActivity_ViewBinding;
import com.example.self_butterknife_client.R;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv1)
    TextView mTextView;

    @BindView(R.id.button1)
    Button mButton;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        mTextView.setText("Activity1-butter-tv1");
        mButton.setText("Activity1-butter-button1");
    }

    void buttonClick() {
        Intent intent = new Intent(MainActivity.this, Activity2.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}