package com.example.d07handler.write.hanlder;

/**
 * Created by hjcai on 2021/7/29.
 */
public class MyActivity extends Activity{
    private TextView mTextView;

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            System.out.println("msg.what 必定=999 "+msg.what);
            mTextView.setText((String)msg.obj);
            System.out.println("handleMessage必定在主线程: "+Thread.currentThread());
        };
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mTextView = findViewById(RR.id.text_view);
        new Thread(){
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 开启子线程
                System.out.println("child thread "+Thread.currentThread());
                Message message = new Message();
                message.obj = "update text";
                message.what = 999;
                // 向在主线程中创建的mHandler发送消息 消息入栈 之前在ActivityThread已经开启轮询Looper.loop();
                // 之后则会被mHandler handle这个message
                mHandler.sendMessage(message);
            };
        }.start();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
