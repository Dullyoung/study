package com.example.study.view.widget;
import android.content.Context;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class PersonScrollView extends ScrollView{

    public PersonScrollView(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;
        }
        return false;
    }
}
