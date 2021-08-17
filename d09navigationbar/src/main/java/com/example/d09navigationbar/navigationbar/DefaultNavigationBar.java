package com.example.d09navigationbar.navigationbar;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.d09navigationbar.R;

public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder>{

    protected DefaultNavigationBar(Builder builder) {
        super(builder);
    }

    @Override
    public void attachNavigationParams() {
        super.attachNavigationParams();
        TextView leftView = findViewById(R.id.back_tv);
        leftView.setVisibility(getBuilder().mLeftVisible);
        // 这里能够访问到mLeftVisible 因为getBuilder返回的是泛型类型
        // DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder> 具现化了B为DefaultNavigationBar.Builder
        // 因此 getBuilder()返回的是DefaultNavigationBar.Builder 能够调用mLeftVisible变量
    }

    public static class Builder extends AbsNavigationBar.Builder<DefaultNavigationBar.Builder>{
        public int mLeftVisible = View.VISIBLE;

        public Builder(Context context, ViewGroup parent) {
            super(context, R.layout.defualt_navigation_bar, parent);
        }

        public Builder setLeftText(String text){
            setText(R.id.back_tv,text);
            return this;
        }

        public Builder setLeftClickListener(View.OnClickListener clickListener){
            setOnClickListener(R.id.back_tv,clickListener);
            return this;
        }

        public Builder hideLeftText() {
            mLeftVisible = View.INVISIBLE;
            return this;
        }

        @Override
        public DefaultNavigationBar create() {
            return new DefaultNavigationBar(this);
        }
    }
}
