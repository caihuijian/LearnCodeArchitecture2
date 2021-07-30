package com.example.d07handler.write.hanlder;



import java.util.Currency;

/**
 * Created by hjcai on 2021/7/28.
 */
public class MessageQueue {// 不是严格意义的队列 入队列按照when入队列 出队则是和队列的规则一致
    // 虽然只有一个Message 然而Message.next 指向下一个Message 依次类推 知道某一个Message的next指向null 其实组成一个队列
    Message mMessages;

    // 按照when的value 从小到大入队列
    boolean enqueueMessage(Message msg, long when) {
        System.out.println("enqueueMessage msg.what " +msg.what);
        synchronized (this) {
            msg.when = when;
            Message p = mMessages;
            if (p == null || when == 0 || when < p.when) {
                // New head, wake up the event queue if blocked.
                msg.next = p;
                mMessages = msg;
            } else {
                // Inserted within the middle of the queue.  Usually we don't have to wake
                // up the event queue unless there is a barrier at the head of the queue
                // and the message is the earliest asynchronous message in the queue.
                Message prev;
                for (; ; ) {
                    prev = p;
                    p = p.next;
                    if (p == null || when < p.when) {
                        break;
                    }
                }
                msg.next = p; // invariant: p == prev.next
                prev.next = msg;
            }
        }
        return true;
    }

    // 从队列头部出队列
    Message next() {
        for (; ; ) {
            synchronized (this) {
                // Try to retrieve the next message.  Return if found.
                final long now = System.currentTimeMillis();
                Message prevMsg = null;
                Message msg = mMessages;
                if (msg != null && msg.target == null) {
                    // Stalled by a barrier.  Find the next asynchronous message in the queue.
                    do {
                        prevMsg = msg;
                        msg = msg.next;
                    } while (msg != null);
                }
                if (msg != null) {
                    if (now < msg.when) {
                        // Next message is not ready.  Set a timeout to wake up when it is ready.
                    } else {
                        // Got a message.
                        if (prevMsg != null) {
                            prevMsg.next = msg.next;
                        } else {
                            mMessages = msg.next;
                        }
                        msg.next = null;
                        return msg;
                    }
                } else {
                    // No more messages.
                }
            }
        }
    }
}
