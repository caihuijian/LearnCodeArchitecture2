package com.example.self_butterknife;

import android.app.Activity;

import androidx.annotation.UiThread;

/**
 * Created by hjcai on 2021/7/19.
 */
public interface Unbinder {
    @UiThread
    void unbind();

    Unbinder EMPTY = new Unbinder() {
        @Override
        public void unbind() {
        }
    };
}
