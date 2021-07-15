package com.example.selfbutterknife;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;

public class Activity2 extends AppCompatActivity {
    @BindView(R.id.tv1)
    TextView mTextView;

    @BindView(R.id.button1)
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        mTextView.setText("Activity2-butter-tv1");
        mButton.setText("Activity2-butter-button1");
    }
}