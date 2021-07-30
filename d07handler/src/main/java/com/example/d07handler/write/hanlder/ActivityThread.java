package com.example.d07handler.write.hanlder;


/**
 * Created by hjcai on 2021/7/29.
 */
public class ActivityThread {
    final H mH = new H();
    public static final int RESUME_ACTIVITY         = 107;
    public static void main(String[] args) {
    	System.out.println("当前是主线程: "+Thread.currentThread());
        // 初始化当前Thread的looper
        Looper.prepare();
        ActivityThread thread = new ActivityThread();
        // 开始Activity的生命周期
        thread.attach(false);
        // 开启消息的轮询
        Looper.loop();
    }

    private void attach(boolean b) {
        Activity myActivity = new MyActivity();
        // onCreate执行
        myActivity.onCreate();
        Message message = new Message();
        // 将Activity传递进Message
        message.obj = myActivity;
        message.what = RESUME_ACTIVITY;
        // message在mH的队列中入队 在轮询之后 会由mH处理Message
        mH.sendMessage(message);
    }

    private class H extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == RESUME_ACTIVITY){// 处理attach中的message
                Activity mainActivity = (Activity) msg.obj;
                mainActivity.onResume();
            }
        }
    }

}
