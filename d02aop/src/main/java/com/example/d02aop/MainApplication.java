package com.example.d02aop;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by hjcai on 2021/7/8.
 */
public class MainApplication extends Application {

    private final static String TAG = "MainApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        listenActivityLifecycleCallbacks();
    }

    private void listenActivityLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                Log.e(TAG, "onActivityCreated: " + activity.getPackageName() + "." + activity.getLocalClassName());
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                Log.e(TAG, "onActivityStarted: " + activity.getPackageName() + "." + activity.getLocalClassName());
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                Log.e(TAG, "onActivityResumed: " + activity.getPackageName() + "." + activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                Log.e(TAG, "onActivityPaused: " + activity.getPackageName() + "." + activity.getLocalClassName());
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                Log.e(TAG, "onActivityStopped: " + activity.getPackageName() + "." + activity.getLocalClassName());
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
                Log.e(TAG, "onActivitySaveInstanceState: " + activity.getPackageName() + "." + activity.getLocalClassName());
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                Log.e(TAG, "onActivityDestroyed: " + activity.getPackageName() + "." + activity.getLocalClassName());
            }
        });
    }
}
