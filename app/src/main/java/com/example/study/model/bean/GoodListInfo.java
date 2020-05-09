package com.example.study.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;



public class GoodListInfo {
    public List<GoodInfo> getGoodInfoList() {
        return goodInfoList;
    }

    public void setGoodInfoList(List<GoodInfo> goodInfoList) {
        this.goodInfoList = goodInfoList;
    }

    @JSONField(name = "vip_list")
    private List<GoodInfo> goodInfoList;
}
