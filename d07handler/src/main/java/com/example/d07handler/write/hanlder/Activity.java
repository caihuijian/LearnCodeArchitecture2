package com.example.d07handler.write.hanlder;

/**
 * Created by hjcai on 2021/7/29.
 */
public class Activity {
    public void onCreate(){
        System.out.println("onCreate execute");
    }

    public void onResume(){
        System.out.println("onResume execute");
    }

    public TextView findViewById(int tv) {
        return new TextView();
    }
}
