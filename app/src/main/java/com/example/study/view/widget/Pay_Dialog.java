package com.example.study.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.study.R;

public class Pay_Dialog extends Dialog {
    private boolean isChanged = true;
    ScrollView scrollView;
    RelativeLayout pay_select11, pay_select22, pay_select33;
    private ImageView pay, pay_select1, pay_select2, pay_select3, pay_wx, pay_ali, pay_delete;

    public Pay_Dialog(Context context) {
        super(context, R.style.no_border_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题
        setContentView(R.layout.pay_main);//引入自定义的my_dialog.xml布局
        pay = (ImageView) findViewById(R.id.pay);
        pay.getParent().requestDisallowInterceptTouchEvent(true);
        pay_delete = (ImageView) findViewById(R.id.pay_delete);
        pay_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        pay_select11 = (RelativeLayout) findViewById(R.id.pay_select11);
        pay_select1 = (ImageView) findViewById(R.id.pay_select1);
        pay_select11.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);//通知父控件srcollview请勿拦截本控件touch事件
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        pay_select1.setImageResource(R.mipmap.pay_select_press);
                        pay_select2.setImageResource(R.mipmap.pay_select_normal);
                        pay_select3.setImageResource(R.mipmap.pay_select_normal);
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
        pay_select22 = (RelativeLayout) findViewById(R.id.pay_select22);
        pay_select2 = (ImageView) findViewById(R.id.pay_select2);
        pay_select22.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_BUTTON_PRESS:
                        break;
                    case MotionEvent.ACTION_UP:
                        pay_select2.setImageResource(R.mipmap.pay_select_press);
                        pay_select1.setImageResource(R.mipmap.pay_select_normal);
                        pay_select3.setImageResource(R.mipmap.pay_select_normal);
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
        pay_select33 = (RelativeLayout) findViewById(R.id.pay_select33);
        pay_select3 = (ImageView) findViewById(R.id.pay_select3);
        pay_select33.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_BUTTON_PRESS:
                        break;
                    case MotionEvent.ACTION_UP:
                        pay_select1.setImageResource(R.mipmap.pay_select_normal);
                        pay_select2.setImageResource(R.mipmap.pay_select_normal);
                        pay_select3.setImageResource(R.mipmap.pay_select_press);
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
        pay_wx = (ImageView) findViewById((R.id.pay_wx));
        pay_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay_wx.setImageResource(R.mipmap.pay_wx_press);
                pay_ali.setImageResource(R.mipmap.pay_ali_normal);
            }
        });
        pay_ali = (ImageView) findViewById(R.id.pay_ali);
        pay_ali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay_ali.setImageResource(R.mipmap.pay_ali_press);
                pay_wx.setImageResource(R.mipmap.pay_wx_normal);
            }
        });


    }

}
