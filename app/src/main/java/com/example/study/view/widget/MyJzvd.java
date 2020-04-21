package com.example.study.view.widget;


import android.content.Context;
import android.util.AttributeSet;

import cn.jzvd.JzvdStd;

/**
 * 全屏状态播放完成，不退出全屏
 * Created by Nathen on 2016/11/26.
 */
public class MyJzvd extends JzvdStd   {
    public MyJzvd(Context context) {
        super(context);
    }

    public MyJzvd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //重写播放完成状态的逻辑 不退出全屏
    @Override
    public void onCompletion() {
        if (screen == SCREEN_FULLSCREEN) {
            onStateAutoComplete();
        } else {
            //gotoScreenNormal();原方法这里是退出全屏回到小屏
            super.onCompletion();
        }

    }
}