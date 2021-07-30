package com.example.d07handler.write.hanlder;

/**
 * Created by hjcai on 2021/7/29.
 */
public class TextView {
    private Thread mThread;
    public TextView(){
        mThread = Thread.currentThread();
    }

    public void setText(CharSequence text){
        checkThread();
        System.out.println("update "+text);
    }

    void checkThread() {
        if (mThread != Thread.currentThread()) {
            throw new RuntimeException(
                    "Only the original thread that created a view hierarchy can touch its views.");
        }
    }
}
