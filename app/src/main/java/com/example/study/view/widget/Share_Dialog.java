package com.example.study.view.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.study.R;
import com.example.study.model.bean.AdvInfoBean;
import com.example.study.model.bean.LoginDataInfo;
import com.example.study.model.bean.ShareInfo;
import com.example.study.model.engin.InitEngin;
import com.kk.securityhttp.domain.ResultInfo;

import java.io.File;
import java.lang.reflect.Type;

import rx.Observer;

import static com.example.study.model.Cache.Cache_Json.dir;
import static com.example.study.model.Cache.Cache_Json.readTextFile;
import static com.example.study.model.Cache.Cache_Json.saveToSDCard;

public class Share_Dialog extends Dialog {
    TextView share_cancle;
    Context context;
    ShareInfo shareInfo;
    String desp = "";

    public Share_Dialog(Context context) {
        super(context, R.style.no_border_dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题
        setContentView(R.layout.share);//引入自定义的my_dialog.xml布局
        if (!new File(dir, "shareIcon.png").exists()) {
            MyUtils.saveImageToSD(BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_share), dir.getAbsolutePath(), "shareIcon.png");
        }
        getData();

        share_cancle = (TextView) findViewById(R.id.cancle_share);
        share_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        findViewById(R.id.share_wx_circle).setOnClickListener(v -> {
            MyUtils.sharedToWXCircle(context, desp, FileProvider.getUriForFile(context, "com.example.study.provider", new File(dir, "shareIcon.png")));
        });
        findViewById(R.id.share_wx).setOnClickListener(v -> {
            MyUtils.shareToWX(context, desp);
        });
        findViewById(R.id.share_qq).setOnClickListener(v -> {
            MyUtils.shareToQQ(context, desp);
        });
        findViewById(R.id.share_qq_zone).setOnClickListener(v -> {
            MyUtils.shareToQQZone(context, desp, FileProvider.getUriForFile(context, "com.example.study.provider", new File(dir, "shareIcon.png")));
        });
    }

    private void getData() {
        if (new File(dir, "shareInfo").exists()) {
            String json = readTextFile("shareInfo");
            Type type = new TypeReference<ResultInfo<LoginDataInfo>>() {
            }.getType();
            ResultInfo<LoginDataInfo> loginDataInfoResultInfo = JSONObject.parseObject(json, type);
            shareInfo = loginDataInfoResultInfo.getData().getShareInfo();
            desp ="\t\t"+ shareInfo.getTitle() + "\n\n\t\t" + shareInfo.getContent()+"\n\n"+context.getString(R.string.downloadUrl);
            return;
        }
        InitEngin initEngin = new InitEngin(context);
        initEngin.getLoginInfo().subscribe(new Observer<ResultInfo<LoginDataInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<LoginDataInfo> loginDataInfoResultInfo) {
                loginDataInfoResultInfo.setResponse(null);
                saveToSDCard((Activity) context, "shareInfo", JSONObject.toJSONString(loginDataInfoResultInfo));
                shareInfo = loginDataInfoResultInfo.getData().getShareInfo();
                desp =context.getString(R.string.space)+ shareInfo.getTitle() + "\n\n"+context.getString(R.string.space) + shareInfo.getContent()+"\n\n"+context.getString(R.string.downloadUrl);
            }
        });

    }
}