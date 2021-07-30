package com.example.d07handler.write.hanlder;



/**
 * Created by hjcai on 2021/7/28.
 */
public class Looper {
    // Looper内部只有一个Looper实例 存储在sThreadLocal
    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
    public MessageQueue mQueue;

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    public Looper() {
        // 一个looper匹配唯一一个队列mQueue
        mQueue = new MessageQueue();
    }

    public static void prepare() {
        if (sThreadLocal.get() != null) {
            // 确保一个Looper只返回唯一一个Looper实例
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        // 在sThreadLocal中存储Looper实例 键值对为 thread-looper
        sThreadLocal.set(new Looper());
    }
    /*
     *    ThreadLocal的set方法
     *     public void set(T value) {
     *         // 获取当前thread
     *         Thread t = Thread.currentThread();
     *         // 以当前thread为key Looper为value 存储在ThreadLocalMap
     *         // 即 ThreadLocalMap 存储了唯一的键值对 thread-looper
     *         // 一个thread只有一个looper 且必须有一个（否则loop时报错）
     *         ThreadLocalMap map = getMap(t);
     *         if (map != null)
     *             map.set(this, value);
     *         else
     *             createMap(t, value);
     *     }
     */

    /**
     * Run the message queue in this thread. Be sure to call
     * {link #quit()} to end the loop.
     */
    public static void loop() {
        final Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        final MessageQueue queue = me.mQueue;
        for (;;) {
            Message msg = queue.next(); // might block
            if (msg == null) {
                // No message indicates that the message queue is quitting.
                return;
            }

            try {
                msg.target.handleMessage(msg);
            } catch (Exception exception) {
                throw exception;
            }
            // 回收消息部分省略
            // msg.recycleUnchecked();
        }
    }
}
