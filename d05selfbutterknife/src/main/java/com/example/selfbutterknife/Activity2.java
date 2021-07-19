package com.example.selfbutterknife;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Activity2 extends AppCompatActivity {
    TextView mTextView;

    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        //mTextView.setText("Activity2-butter-tv2");
        //mButton.setText("Activity2-butter-button2");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}