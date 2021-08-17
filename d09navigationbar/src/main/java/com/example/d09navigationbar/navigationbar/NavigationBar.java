package com.example.d09navigationbar.navigationbar;

import android.content.Context;
import android.view.ViewGroup;

public class NavigationBar extends AbsNavigationBar{
    protected NavigationBar(Builder builder) {
        super(builder);
    }

    public static class Builder extends AbsNavigationBar.Builder<NavigationBar.Builder> {// 这里的泛型覆盖父类中的泛型（父类中泛化 子类中具现） 使得setText setOnClickListener返回的B为NavigationBar.Builder
        public Builder(Context context, int layoutId, ViewGroup parent) {
            super(context, layoutId, parent);
        }

        @Override
        public NavigationBar create() {
            return new NavigationBar(this);
        }
    }
}
