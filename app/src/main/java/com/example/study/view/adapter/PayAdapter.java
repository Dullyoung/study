package com.example.study.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.study.R;
import com.example.study.model.bean.GoodInfo;
import com.example.study.model.bean.GoodListInfo;

import java.util.ArrayList;
import java.util.List;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.PayHolder> {
    Context context;
    GoodListInfo goodListInfo;
      List<Boolean> selected=new ArrayList<Boolean>(){};
public int getSelectedPosition(){
    for (int i=0;i<selected.size();i++){
        if (selected.get(i)){
            return i;
        }
    }
    return 0;
}
    public void setConfig(Context context, GoodListInfo goodListInfo) {
        this.context = context;
        this.goodListInfo = goodListInfo;
        for (int i=0;i<getItemCount();i++){
            if (i==0){
                selected.add(true);

            }else
            selected.add(false);
        }
    }

    @NonNull
    @Override
    public PayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.good_list, null);
        return new PayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PayHolder holder, int position) {
        if (position == 0) {
            holder.number.setImageResource(R.mipmap.good_info_num1);
        }
        if (position ==1) {
            holder.number.setImageResource(R.mipmap.good_info_num2);
        }
        if (position == 2) {
            holder.number.setImageResource(R.mipmap.good_info_num3);
        }
        if (position == 3) {
            holder.number.setImageResource(R.mipmap.good_info_num4);
        }
        if (selected.get(position)){
            holder.select.setImageResource(R.mipmap.pay_select_press);
        }else {
         holder.select.setImageResource(R.mipmap.pay_select_normal);
        }
      holder.select.setOnClickListener(v -> {
            setSelected();
            selected.set(position,true);
            notifyDataSetChanged();
      });
        holder.left.setOnClickListener(v -> {
            setSelected();
            selected.set(position,true);
            notifyDataSetChanged();
        });
        holder.title.setText(goodListInfo.getGoodInfoList().get(position).getTitle());
        holder.desp.setText(goodListInfo.getGoodInfoList().get(position).getSub_title());
        holder.real.setText("¥"+goodListInfo.getGoodInfoList().get(position).getReal_price());
        holder.origin.setText("原价¥"+goodListInfo.getGoodInfoList().get(position).getPrice());
    }

private void setSelected(){
        for (int i=0;i<selected.size();i++){
            selected.set(i,false);
        }
}


    @Override
    public int getItemCount() {
        return goodListInfo.getGoodInfoList().size();
    }

    public class PayHolder extends RecyclerView.ViewHolder {
        TextView title, desp, real, origin;
        ImageView number, select;
        RelativeLayout left;
        public PayHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_tv);
            desp = itemView.findViewById(R.id.desp_tv);
            real = itemView.findViewById(R.id.real_price);
            origin = itemView.findViewById(R.id.original_price);
            number = itemView.findViewById(R.id.goodInfo_number);
            select = itemView.findViewById(R.id.select_btn);
            left=itemView.findViewById(R.id.left);
        }
    }
}
