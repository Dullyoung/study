package com.example.study.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.study.R;

import java.util.List;

public class PhonticAdapter extends RecyclerView.Adapter<PhonticAdapter.PhonticHolder> {
    Context context;
    int[] imgs;
    List<String> id;
private OnImgClickListener clickListener;
public void setOnImgClickListener(OnImgClickListener onImgClickListener){
    this.clickListener=onImgClickListener;
}
    public void setConfig(Context context, int[] imgs, List<String> id) {
        this.context = context;
        this.imgs = imgs;
        this.id = id;
    }

    @NonNull
    @Override
    public PhonticHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhonticHolder(LayoutInflater.from(context).inflate(R.layout.class_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull PhonticHolder holder, final int position) {
            holder.iv.setImageDrawable(context.getResources().getDrawable(imgs[position]));
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.OnClick(position,id.get(position));
                }
            });
    }

    @Override
    public int getItemCount() {
        return imgs.length;
    }

    class PhonticHolder extends RecyclerView.ViewHolder {
        ImageView iv;

        public PhonticHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
        }
    }
    public interface OnImgClickListener{
        public void OnClick(int position,String id);
    }
}

