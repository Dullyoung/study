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

public class PhonticAdapter extends RecyclerView.Adapter<PhonticAdapter.PhoneticHolder> {
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
    public PhoneticHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhoneticHolder(LayoutInflater.from(context).inflate(R.layout.class_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneticHolder holder, final int position) {
    if (getItemCount()>0){
        holder.iv.setImageDrawable(context.getResources().getDrawable(imgs[position]));
        holder.iv.setOnClickListener(v -> clickListener.OnClick(position,id.get(position)));
    }

    }

    @Override
    public int getItemCount() {
    if (imgs==null){
        return 0;
    }
        return imgs.length;
    }

    class PhoneticHolder extends RecyclerView.ViewHolder {
        ImageView iv;

        public PhoneticHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
        }
    }
    public interface OnImgClickListener{
          void OnClick(int position,String id);
    }
}

