package com.example.study.model.engin;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.example.study.model.bean.PhonticDetailBean;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.BaseEngin;
import com.kk.securityhttp.engin.HttpCoreEngin;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;


public class PhoneticDetailEngin extends BaseEngin {
    private Context context;

    public PhoneticDetailEngin(Context context) {
        super(context);

        this.context = context;
    }
//微课详情

    //api接口地址
    @Override
    public String getUrl() {
        return "http://tic.upkao.com/api/weike/detail?app_id=5";
    }
//参数如下：
//{"imeil":"1771181c2a9b2474","device_type":"2","app_version":"31",
// "encrypt_response":"true","id":"236","agent_id":"1","sv":"AAA  5.1.1"}
    public Observable<ResultInfo<PhonticDetailBean>> getPhoneticDetailInfo(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("app_version", "31");
        //其他的参数全局化设置的默认参数
        HttpCoreEngin<ResultInfo<PhonticDetailBean>> engin = HttpCoreEngin.get(context);
        Observable<ResultInfo<PhonticDetailBean>> rxpost = engin.rxpost(getUrl(),
                new TypeReference<ResultInfo<PhonticDetailBean>>() {}.getType(),
                map, true, true, true);




        return rxpost;
    }

}
