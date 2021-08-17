package com.example.d09navigationbar.navigationbar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public interface INavigation {
    void createNavigationBar();

    void attachNavigationParams();

    //TextView findViewById(int viewId);

    void attachParent(View navigationBar, ViewGroup parent);

    //AbsNavigationBar.Builder getBuilder();
}
