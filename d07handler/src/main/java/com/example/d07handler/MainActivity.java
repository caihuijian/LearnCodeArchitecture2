package com.example.d07handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    CustomHandler myHandler;
    private static final int MSG_INDEX_1 = 1111;
    private static final int MSG_INDEX_2 = 2222;
    private static final int MSG_INDEX_3 = 3333;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.tv1);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                myHandler = new CustomHandler(MainActivity.this);
                Message message = new Message();
                message.what = MSG_INDEX_1;
                myHandler.sendMessageDelayed(message,1);
                myHandler.sendEmptyMessageDelayed(MSG_INDEX_2, 1000000000);
                myHandler.sendEmptyMessageDelayed(MSG_INDEX_3, 500000000);
                myHandler.sendEmptyMessageDelayed(4444, 4000000);
                Looper.loop();
            }
        });
        thread.start();

    }

    class CustomHandler extends Handler {
        WeakReference<MainActivity> mWeakReference;

        public CustomHandler(MainActivity activity) {
            mWeakReference = new WeakReference(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_INDEX_1:
                    tv1.setText("AAAAA");
                    Toast.makeText(mWeakReference.get(), "1111", Toast.LENGTH_SHORT).show();
                    break;
                case MSG_INDEX_2:
                    Toast.makeText(mWeakReference.get(), "2222", Toast.LENGTH_SHORT).show();
                    break;
                case MSG_INDEX_3:
                    Toast.makeText(mWeakReference.get(), "3333", Toast.LENGTH_SHORT).show();
                    break;
                default:
            }
        }
    }
}

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        tv1 = findViewById(R.id.tv1);
////        myHandler = new MyHandler(this);
//        myHandler.sendEmptyMessageDelayed(MSG_INDEX_1, 100);
//        myHandler.sendEmptyMessageDelayed(MSG_INDEX_2, 10000);
//        myHandler.sendEmptyMessageDelayed(MSG_INDEX_3, 5000);
//
//        Thread thread = new Thread(() -> {
////            Looper.prepare();
//            new Handler(Looper.myLooper());
////            Looper.loop();
//        });
//        thread.start();
//    }