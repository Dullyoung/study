package com.example.study.model.engin;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.example.study.model.bean.PhoneticIndexBean;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.BaseEngin;
import com.kk.securityhttp.engin.HttpCoreEngin;

import rx.Observable;

//微课列表
public class PhoneticIndexEngin extends BaseEngin {
    public PhoneticIndexEngin(Context context) {
        super(context);
    }

    @Override
    public String getUrl() {
        return "http://tic.upkao.com/api/weike/index?app_id=5";
    }

    public Observable<ResultInfo<PhoneticIndexBean>> getPhonticIndex(Context context) {
        HttpCoreEngin<ResultInfo<PhoneticIndexBean>> engin = HttpCoreEngin.get(context);
        Observable<ResultInfo<PhoneticIndexBean>> rxpost = engin.rxpost(getUrl(),new TypeReference<ResultInfo<PhoneticIndexBean>>() {
        }.getType(), null, true, true, true);
        return rxpost;
    }
}
