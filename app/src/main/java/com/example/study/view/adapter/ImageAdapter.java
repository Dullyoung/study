package com.example.study.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.study.R;
import com.example.study.model.Cache.GlideApp;
import com.example.study.model.Interface.OnItemClickListener;
import com.example.study.model.bean.PageChanger;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{
    private List<String> url;
    private  Context context;
    RequestOptions options ;
    public void setUp(Context context,List<String> url){
        this.context=context;
        this.url=url;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        options= new RequestOptions();
        options.placeholder(R.mipmap.ic_player_error);
        options.error(R.mipmap.ic_player_error);
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.skipMemoryCache(true);
        View view= LayoutInflater.from(context).inflate(R.layout.image_item_layout,null);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        GlideApp.with(context).load(url.get(position)).apply(options).thumbnail(0.1f).into(holder.iv);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new PageChanger("read",position));
                onItemClickListener.OnClick();
            }
        });
    }


    OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    @Override
    public int getItemCount() {
        return url.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.iv);
        }
    }
}
