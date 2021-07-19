package com.example.selfbutterknife;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Activity2 extends AppCompatActivity {
    @BindView(R.id.tv1)
    TextView mTextView;

    @BindView(R.id.button1)
    Button mButton;

    Unbinder unbinder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        unbinder = ButterKnife.bind(this);

        mTextView.setText("Activity2-butter-tv2");
        mButton.setText("Activity2-butter-button2");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}