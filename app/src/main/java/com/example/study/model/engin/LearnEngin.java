package com.example.study.model.engin;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.example.study.model.bean.LearnInfoWrapper;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.BaseEngin;

import rx.Observable;

public class LearnEngin extends BaseEngin {
    public LearnEngin(Context context) {
        super(context);
    }
    @Override
    public String getUrl() {
        return "http://tic.upkao.com/api/index/phonetic_list?app_id=5";
    }
    public Observable<ResultInfo<LearnInfoWrapper>> getLearnInfo(){
        return rxpost(new TypeReference<ResultInfo<LearnInfoWrapper>>(){}.getType(), null, null, true, true, true);
    }

}