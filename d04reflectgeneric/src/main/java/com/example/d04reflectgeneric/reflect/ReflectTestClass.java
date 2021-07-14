package com.example.d04reflectgeneric.reflect;

import android.util.Log;

/**
 * Created by hjcai on 2021/7/14.
 */
public class ReflectTestClass {
    private static final String TAG = "ReflectTestClass";

    private String mName = "hjcai";

//    public ReflectTestClass() {
//    }

    private ReflectTestClass() {
        Log.e(TAG, "Constructor without parameters is called");
    }

    private ReflectTestClass(String name) {
        mName = name;
        Log.e(TAG, "Constructor with parameter String called");
    }

    private String getName() {
        Log.e(TAG, "getName: " + mName);
        return mName;
    }

    private void setName(String mName) {
        this.mName = mName;
        Log.e(TAG, "setName: " + mName);
    }
}
