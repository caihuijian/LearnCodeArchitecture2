package com.example.d08singleton.manager;

import android.app.Activity;

import java.util.Iterator;
import java.util.Objects;
import java.util.Stack;

/**
 * Created by hjcai on 2021/8/4.
 */
public class ActivityManager {
    private static volatile ActivityManager mInstance;
    private final Stack<Activity> mActivities;

    private ActivityManager() {
        mActivities = new Stack<>();
    }

    public static ActivityManager getInstance() {
        if (mInstance == null) {
            synchronized (ActivityManager.class) {
                if (mInstance == null) {
                    mInstance = new ActivityManager();
                }
            }
        }
        return mInstance;
    }

    // 创建Activity时调用
    public void attach(Activity activity) {
        mActivities.add(activity);
    }

    // 移除Activity时调用
    public void detach(Activity detachActivity) {
        mActivities.removeIf(activity -> activity == detachActivity);
    }

    // 关闭指定 Activity
    public void finish(Activity finishActivity) {
        Iterator<Activity> activityIterator = mActivities.iterator();
        while (activityIterator.hasNext()) {
            Activity activity = activityIterator.next();
            if (activity == finishActivity) {
                activityIterator.remove();
                activity.finish();
            }
        }
    }

    /**
     * for 去移除有问题
     * Caused by: java.util.ConcurrentModificationException
     * at java.util.Vector$Itr.checkForComodification(Vector.java:1200)
     * at java.util.Vector$Itr.next(Vector.java:1147)
     */
    // 根据Activity的类名关闭 Activity
    public void finish(Class<? extends Activity> activityClass) {
            /*for (Activity activity : mActivities) {
                if (activity.getClass().getCanonicalName().equals(activityClass.getCanonicalName())) {
                    mActivities.remove(activity);
                    activity.finish();
                }
            }*/
        Iterator<Activity> activityIterator = mActivities.iterator();
        while (activityIterator.hasNext()) {
            Activity activity = activityIterator.next();
            if (Objects.equals(activity.getClass().getCanonicalName(), activityClass.getCanonicalName())) {
                activityIterator.remove();
                activity.finish();
            }
        }
    }

    /**
     * 退出整个应用
     */
    public void exitApplication() {
        Iterator<Activity> activityIterator = mActivities.iterator();
        while (activityIterator.hasNext()) {
            Activity activity = activityIterator.next();
            activityIterator.remove();
            activity.finish();
        }
        System.exit(0);
    }

    // 获取当前的（栈顶）Activity
    public Activity currentActivity() {
        return mActivities.lastElement();
    }
}
