package com.example.study.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.study.R;

public class DownLoadDialog extends Dialog {
    private TextView title, message, exit, know;
    Context context;
    String uri;

    public DownLoadDialog(@NonNull Context context, String uri) {
        super(context, R.style.no_border_dialog);
        this.context = context;
        this.uri = uri;
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
        title.setText("版本更新");
        message.setText("\t\t有新版本可以更新啦~快来下载更新吧~");
        exit.setText("退出");
        know.setText("立即下载");
        exit.setOnClickListener(v -> {
            dismiss();
        });
        know.setOnClickListener(v -> {
            Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(uri));
            context.startActivity(viewIntent);
            dismiss();
        });
    }
}
