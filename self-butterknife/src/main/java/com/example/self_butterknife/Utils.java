package com.example.self_butterknife;

import android.app.Activity;
import android.view.View;


/**
 * Created by hjcai on 2021/7/19.
 */
public class Utils {
    public static <T extends View> T findViewById(Activity activity, int viewId) {
        return activity.findViewById(viewId);
    }
}
