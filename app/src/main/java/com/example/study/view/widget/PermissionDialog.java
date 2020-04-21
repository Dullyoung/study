package com.example.study.view.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.study.R;

public class PermissionDialog extends Dialog {
    private Activity activity;
    String[] permissions;

    public PermissionDialog(@NonNull Context context, String[] permissions) {
        super(context, R.style.no_border_dialog);
        activity = (Activity) context;
        this.permissions = permissions;
    }
    private TextView title, message, exit, know;

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
        exit.setText("退出");
        know.setText("去授权");
        message.setText("\t\t\t\t本软件内容多为音视频，会消耗大量数据流量流量，请允许读写权限将音视频缓存至本地减少数据流量消耗。");
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
        know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                ActivityCompat.requestPermissions(activity, permissions, 1);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        System.exit(0);
        return true;
    }
}
