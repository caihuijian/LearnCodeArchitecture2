package com.example.d07handler.write.hanlder;

/**
 * Created by hjcai on 2021/7/28.
 */
public class Message {
    // 何时执行Message
    public long when;
    // 用于区分各个Message
    public int what;
    // 标记当前Message由哪个handler处理
    public Handler target;
    // 链表结构 指向下一个Message
    Message next;
    // 用于通过Message传递信息给Handler
    public Object obj;
}
