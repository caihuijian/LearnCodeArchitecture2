package com.example.self_butterknife;

import androidx.annotation.UiThread;

/**
 * Created by hjcai on 2021/7/19.
 */
public interface Unbinder {
    @UiThread
    void unbind();

    Unbinder EMPTY = () -> {
    };
}
