package com.example.study.view.widget;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.study.R;

public class Share_Dialog extends Dialog{
    TextView share_cancle;
    public Share_Dialog(Context context){
        super(context, R.style.no_border_dialog);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题
        setContentView(R.layout.share);//引入自定义的my_dialog.xml布局
        share_cancle=(TextView) findViewById(R.id.cancle_share);
        share_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        dismiss();
            }
        });




    }

}