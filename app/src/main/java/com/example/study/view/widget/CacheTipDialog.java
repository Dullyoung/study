package com.example.study.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.study.R;
import com.example.study.model.Cache.CacheUtils;

public class CacheTipDialog extends Dialog {
    private TextView title, message, exit, know;
    Context context;
    public CacheTipDialog(@NonNull Context context) {
        super(context, R.style.no_border_dialog);
        this.context=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.permission_dialog_layout);
        title = findViewById(R.id.title_message);
        message = findViewById(R.id.content_message);
        exit = findViewById(R.id.exit);
        know = findViewById(R.id.know);
        title.setText("提示");
        exit.setText("取消");
        know.setText("确定");
        message.setText("\t\t\t\t是否确定清除缓存，清除后音视频再次播放需要重新消耗流量！");
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             dismiss();
            }
        });
        know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheUtils.clearAllCache(context);
               onButtonClickListener.Sure();
                dismiss();
            }
        });
    }
   private OnButtonClickListener onButtonClickListener;
    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener)
    {
        this.onButtonClickListener=onButtonClickListener;
    }
  public    interface OnButtonClickListener{
        public void Sure();
    }
}
