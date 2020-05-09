package com.example.study.model.engin;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.example.study.model.bean.LoginDataInfo;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.BaseEngin;


import rx.Observable;



public class InitEngin extends BaseEngin<ResultInfo<LoginDataInfo>> {
    public InitEngin(Context context) {
        super(context);
    }

    @Override
    public String getUrl() {
        return  "http://tic.upkao.com/api/index/init?app_id=5";
    }

    public Observable<ResultInfo<LoginDataInfo>> getLoginInfo() {
        return rxpost(new TypeReference<ResultInfo<LoginDataInfo>>() {
        }.getType(), null, true, true, true);
    }
}
