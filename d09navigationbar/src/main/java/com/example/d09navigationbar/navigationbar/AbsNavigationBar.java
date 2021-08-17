package com.example.d09navigationbar.navigationbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public abstract class AbsNavigationBar <B extends AbsNavigationBar.Builder> implements INavigation {
    private final B mBuilder;
    private View mNavigationBar;

    protected AbsNavigationBar(B builder) {
        this.mBuilder = builder;
        createNavigationBar();
    }

    @Override
    public void createNavigationBar() {
        mNavigationBar = LayoutInflater.from(mBuilder.mContext)
                .inflate(mBuilder.mLayoutId, mBuilder.mParent, false);// 实际上false改成true可以不用调用下面attachParent的方法
        // 将布局添加到父容器
        attachParent(mNavigationBar, mBuilder.mParent);
        // 绑定参数到Builder内部
        attachNavigationParams();
    }

    // 利用AbsNavigationBar引用绑定参数到AbsNavigationBar内部类Builder
    @Override
    public void attachNavigationParams() {
        // 设置文本
        Map<Integer, CharSequence> textMaps = mBuilder.mTextMaps;
        for (Map.Entry<Integer, CharSequence> entry : textMaps.entrySet()) {
            TextView textView = findViewById(entry.getKey());
            if (textView == null) {
                throw new IllegalArgumentException("TextView should not be null");
            }
            textView.setText(entry.getValue());
        }
        // 设置点击事件
        Map<Integer, View.OnClickListener> clickListenerMaps = mBuilder.mClickListenerMaps;
        for (Map.Entry<Integer, View.OnClickListener> entry : clickListenerMaps.entrySet()) {
            View view = findViewById(entry.getKey());
            if (view == null) {
                throw new IllegalArgumentException("view should not be null");
            }
            view.setOnClickListener(entry.getValue());
        }
    }

    @Override
    public void attachParent(View navigationBar, ViewGroup parent) {
        parent.addView(navigationBar, 0);
    }

    // 返回泛型类型 前面的T是泛型声明 后面的T是返回类型
    protected  <T extends View> T findViewById(int viewId) {
        return mNavigationBar.findViewById(viewId);
    }

    protected B getBuilder() {
        return mBuilder;
    }


    /**
     * 构建类 主要任务是存储参数
     */
    public static abstract class Builder<B extends Builder> {// 类泛型 里面用到泛型B 继承自当前的内部类builder
        public Context mContext;
        public int mLayoutId;
        public ViewGroup mParent;
        public Map<Integer, CharSequence> mTextMaps;
        public Map<Integer, View.OnClickListener> mClickListenerMaps;

        public Builder(Context context, int layoutId, ViewGroup parent) {
            this.mContext = context;
            this.mLayoutId = layoutId;
            this.mParent = parent;
            this.mTextMaps = new HashMap<>();
            this.mClickListenerMaps = new HashMap<>();
        }

        // 创建NavigationBar
        public abstract AbsNavigationBar create();

        // 利用builder设置文本
        public B setText(int viewId, String text) {
            mTextMaps.put(viewId, text);
            return (B) this;
        }

        // 利用builder设置点击事件
        public B setOnClickListener(int viewId, View.OnClickListener clickListener) {
            mClickListenerMaps.put(viewId, clickListener);
            return (B) this;
        }
    }
}
