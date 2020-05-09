package com.example.study.model.engin;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.example.study.model.bean.GoodListInfo;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.BaseEngin;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


public class GoodEngin extends BaseEngin {
    public GoodEngin(Context context) {
        super(context);
    }
//http://tic.upkao.com/api/index/vip_list?app_id=5
    @Override
    public String getUrl() {
        return "http://tic.upkao.com/api/index/vip_list?app_id=5";
    }

    public Observable<ResultInfo<GoodListInfo>> getGoodList() {
        Map<String ,String> params=new HashMap<>();
        params.put("encrypt_response","true");
        return rxpost(new TypeReference<ResultInfo<GoodListInfo>>(){}.getType(), params,true, true, true);
    }

}
