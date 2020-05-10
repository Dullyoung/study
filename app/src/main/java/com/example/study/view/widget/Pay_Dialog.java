package com.example.study.view.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.study.R;
import com.example.study.model.bean.GoodListInfo;
import com.example.study.model.bean.LearnInfoWrapper;
import com.example.study.model.engin.GoodEngin;
import com.example.study.view.adapter.PayAdapter;
import com.kk.securityhttp.domain.ResultInfo;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Observer;

import static com.example.study.model.Cache.Cache_Json.dir;
import static com.example.study.model.Cache.Cache_Json.readTextFile;
import static com.example.study.model.Cache.Cache_Json.saveToSDCard;

public class Pay_Dialog extends Dialog {
    String TAG = "aaaa";
    GoodListInfo goodListInfo;
    Context context;
    ImageView wx, ali;
    RecyclerView recyclerView;
    boolean wxPay=true;
    PayAdapter adapter = new PayAdapter();

    public Pay_Dialog(Context context) {
        super(context, R.style.no_border_dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题
        setContentView(R.layout.pay_main);//引入自定义的my_dialog.xml布局
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        Point point = new Point();
        setCanceledOnTouchOutside(false);
        window.getWindowManager().getDefaultDisplay().getSize(point);
        window.setLayout(point.x, WindowManager.LayoutParams.WRAP_CONTENT);
        recyclerView = findViewById(R.id.good_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        getData();

        findViewById(R.id.iv_pay_close).setOnClickListener(v -> {
            dismiss();
        });
        findViewById(R.id.iv_pay_charge).setOnClickListener(v -> {
            SharedPreferenceUtil.write(context,"vip",true);
            String price=goodListInfo.getGoodInfoList().get(adapter.getSelectedPosition()).getReal_price();
            String text=goodListInfo.getGoodInfoList().get(adapter.getSelectedPosition()).getTitle();
            if (wxPay){
                Toast.makeText(context, "微信支付", Toast.LENGTH_SHORT).show();
            }else {
                MyUtils.goToAliPayTransferMoneyPerson(context,price,text,"2088522744286013");
                Toast.makeText(context, "支付宝支付", Toast.LENGTH_SHORT).show();
            }
        });

        wx = findViewById(R.id.iv_wx_pay);
        ali = findViewById(R.id.iv_ali_pay);
        wx.setOnClickListener(v -> {
            wxPay=true;
            wx.setImageResource(R.mipmap.pay_wx_press);
            ali.setImageResource(R.mipmap.pay_ali_normal);
        });
        ali.setOnClickListener(v -> {
            ali.setImageResource(R.mipmap.pay_ali_press);
            wx.setImageResource(R.mipmap.pay_wx_normal);
            wxPay=false;
        });
    }

    private void getData() {

        if (new File(dir, "GoodInfo").exists()) {
            String ResultLocal = readTextFile("GoodInfo");//本地获取到的Json数据
            Type type = new TypeReference<ResultInfo<GoodListInfo>>() {
            }.getType();
            ResultInfo<GoodListInfo> json = com.alibaba.fastjson.JSONObject.parseObject(ResultLocal, type);
            goodListInfo = json.getData();
            adapter.setConfig(context, goodListInfo);
            recyclerView.setAdapter(adapter);
            return;
        }

        GoodEngin engin = new GoodEngin(context);
        engin.getGoodList().subscribe(new Observer<ResultInfo<GoodListInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<GoodListInfo> goodListInfoResultInfo) {
                goodListInfoResultInfo.setResponse(null);
                saveToSDCard((Activity) context, "GoodInfo", JSON.toJSONString(goodListInfoResultInfo));
                goodListInfo = goodListInfoResultInfo.getData();
                adapter.setConfig(context, goodListInfo);
                recyclerView.setAdapter(adapter);
            }
        });
    }


}
