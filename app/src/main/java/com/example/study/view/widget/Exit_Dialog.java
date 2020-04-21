package com.example.study.view.widget;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import com.example.study.R;

public class Exit_Dialog extends Dialog{
    private String dialogname;
    private TextView tvMsg;
    private ImageView btnOK;
    private ImageView btnCancle;
    boolean isChanged=true;
    public Exit_Dialog(Context context, String dialogname){
        super(context, R.style.no_border_dialog);
        this.dialogname=dialogname;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题
        setContentView(R.layout.my_dialog);//引入自定义的my_dialog.xml布局
        tvMsg=(TextView) findViewById(R.id.tv_msg);
        btnOK=(ImageView) findViewById(R.id.btn_ok);
        btnCancle=(ImageView) findViewById(R.id.btn_cancle);
        tvMsg.setText(dialogname);
        btnOK.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                        System.exit(0);//退出程序
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        dismiss();//关闭当前对话框
            }
        });

    }
}
