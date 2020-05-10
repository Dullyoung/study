package com.example.study;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.danikula.videocache.HttpProxyCacheServer;
import com.example.study.model.bean.AdvInfoBean;
import com.example.study.model.engin.AdvEngine;
import com.example.study.view.widget.SharedPreferenceUtil;
import com.kk.securityhttp.domain.GoagalInfo;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.net.contains.HttpConfig;

import com.kk.utils.FileUtil;
import com.kk.utils.LogUtil;
import com.kk.utils.PreferenceUtil;
import com.kk.utils.TaskUtil;


import java.io.File;
import java.util.HashMap;
import java.util.Map;



import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by zhangkai on 2017/10/17.
 */

public class App extends Application {
    private static App INSTANSE;
    public static boolean is_vip=false;
    @Override
    public void onCreate() {
        super.onCreate();
        Observable.just("").subscribeOn(Schedulers.io()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                initGoagal(getApplicationContext());

            }
        }) ;

        INSTANSE = this;

    }

    public static App getApp() {
        return INSTANSE;
    }

    public static void initGoagal(Context context) {
        //全局信息初始化
        GoagalInfo.get().init(context);
       is_vip= SharedPreferenceUtil.readBoolean(context,"vip");
//        //设置文件唯一性 防止手机相互拷贝
//        FileUtil.setUuid(GoagalInfo.get().uuid);

        //设置http默认参数
        String agent_id = "1";
        Map<String, String> params = new HashMap<>();
        if (GoagalInfo.get().channelInfo != null && GoagalInfo.get().channelInfo.agent_id != null) {
            params.put("from_id", GoagalInfo.get().channelInfo.from_id + "");
            params.put("author", GoagalInfo.get().channelInfo.author + "");
            agent_id = GoagalInfo.get().channelInfo.agent_id;
        }
        params.put("agent_id", agent_id);
        params.put("imeil", GoagalInfo.get().uuid);

        String sv = getSV();
        params.put("sv", sv);
        params.put("device_type", "2");
        if (GoagalInfo.get().packageInfo != null) {
            params.put("app_version", GoagalInfo.get().packageInfo.versionCode + "");
        }
        HttpConfig.setDefaultParams(params);


    }

    public static String getSV() {
        return Build.MODEL.contains(Build.BRAND) ? Build.MODEL + " " + Build.VERSION.RELEASE : Build.BRAND + " " + Build.MODEL + " " + Build.VERSION.RELEASE;
    }
















}
