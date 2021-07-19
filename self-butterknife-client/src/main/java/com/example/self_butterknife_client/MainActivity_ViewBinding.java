// Generated code from Butter Knife. Do not modify!
package com.example.self_butterknife_client;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.UiThread;


public class MainActivity_ViewBinding {
    private MainActivity target;

    private View view7f080058;

    @UiThread
    public MainActivity_ViewBinding(MainActivity target) {
        this(target, target.getWindow().getDecorView());
    }

    @UiThread
    public MainActivity_ViewBinding(final MainActivity target, View source) {
        this.target = target;

        View view;
        target.mTextView = Utils.findRequiredViewAsType(source, R.id.tv1, "field 'mTextView'", TextView.class);
        view = Utils.findRequiredView(source, R.id.button1, "field 'mButton' and method 'buttonClick'");
        target.mButton = Utils.castView(view, R.id.button1, "field 'mButton'", Button.class);
        view7f080058 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View p0) {
                target.buttonClick();
            }
        });
    }

}
