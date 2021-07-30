package com.example.d07handler.write.hanlder;

/**
 * Created by hjcai on 2021/7/29.
 */
public class MyActivity extends Activity{
    private TextView mTextView;

    private Handler mHandler = new Handler(){
        // 由于Handler在主线程创建 Handler构造方法Looper.myLooper时取得了主线程的Looper
        // handleMessage在主线程执行
        public void handleMessage(Message msg) {
            System.out.println("msg.what 必定=999 "+msg.what);
            mTextView.setText((String)msg.obj);
            System.out.println("handleMessage在主线程执行: "+Thread.currentThread());
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
                // mTextView.setText("");
                // Only the original thread that created a view hierarchy can touch its views.
                // 子线程禁止直接操作UI

                // 如果想在子线程创建Handler 需要调用Looper.prepare 否则抛出异常
                // Can't create handler inside thread that has not called Looper.prepare()

                // Looper.prepare();
                // Handler my = new Handler();
                // Looper.loop();

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
