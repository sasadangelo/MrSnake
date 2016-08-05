package org.androidforfun.snakoid.framework.impl;

import java.util.List;

import android.view.View.OnTouchListener;

import org.androidforfun.snakoid.framework.Input.TouchEvent;

public interface TouchHandler extends OnTouchListener {
    boolean isTouchDown(int pointer);
    int getTouchX(int pointer);
    int getTouchY(int pointer);
    List<TouchEvent> getTouchEvents();
}
