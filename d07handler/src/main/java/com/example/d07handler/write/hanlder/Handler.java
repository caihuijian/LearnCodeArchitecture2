package com.example.d07handler.write.hanlder;


/**
 * Created by hjcai on 2021/7/28.
 */
public class Handler {
    MessageQueue mQueue;

    public Handler() {
        Looper looper = Looper.myLooper();
        if(looper == null){// 创建Handler之前必须调用Looper.prepare
            // 即创建Handler之前 必须能根据当前线程取出当前线程的Looper 否则报错
            throw new RuntimeException(
                    "Can't create handler inside thread that has not called Looper.prepare()");
        }
        // 将一一对应的Looper-Queue中的Queue赋值给当前handler中的Queue
        // 最终形成 Handler-Looper-Thread 的相互对应的关系
        //          |       |
        //          Q u e u e
        mQueue= looper.mQueue;
    }

    // 发送消息 本质是入队列
    public void sendMessage(Message message) {
        sendMessageDelayed(message,0);
    }

    // 发送消息 本质是入队列
    public final boolean sendMessageDelayed(Message msg, long delayMillis)
    {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return sendMessageAtTime(msg, System.currentTimeMillis() + delayMillis);
    }

    // 发送消息 本质是入队列
    public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        MessageQueue queue = mQueue;
        if (queue == null) {
            return false;
        }
        return enqueueMessage(queue, msg, uptimeMillis);
    }

    public void handleMessage(Message msg) {
    }

    // 发送消息 本质是入队列
    private boolean enqueueMessage(MessageQueue queue, Message msg,
                                   long uptimeMillis) {
        // 确认Message交由本handler处理
    	msg.target = this;
        return queue.enqueueMessage(msg, uptimeMillis);
    }


}
