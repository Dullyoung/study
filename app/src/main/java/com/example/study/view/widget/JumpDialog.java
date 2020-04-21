package com.example.study.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.study.R;
import com.example.study.model.Interface.OnItemClickListener;
import com.example.study.view.adapter.ImageAdapter;

import java.util.List;

public class JumpDialog extends Dialog {
    Context context;
    RecyclerView rv;
    ImageAdapter adapter;
    public JumpDialog(@NonNull Context context) {
        super(context, R.style.no_border_dialog);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view= LayoutInflater.from(context).inflate(R.layout.jump_layout,null);
        Point point=new Point();
        getWindow().getWindowManager().getDefaultDisplay().getSize(point);
        int width=point.x/5*4;
        int height=point.y/5*4;
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(width,height);
        setContentView(view,params);
        rv= view.findViewById(R.id.jump_rv);
        rv.setVisibility(View.VISIBLE);
        rv.setLayoutManager(new GridLayoutManager(context,3));
        rv.setAdapter(adapter);
        findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    public void setRv(List<String> url){ adapter=new ImageAdapter();
       adapter.setUp(context,url);
       adapter.setOnItemClickListener(new OnItemClickListener() {
           @Override
           public void OnClick() {
               dismiss();
           }
       });
       adapter.notifyDataSetChanged();
    }


}
