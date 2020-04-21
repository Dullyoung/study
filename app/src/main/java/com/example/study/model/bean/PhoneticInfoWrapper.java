package com.example.study.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class PhoneticInfoWrapper {
    @JSONField(name = "list")
    private List<PhoneticInfo> phoneticInfoList;

    public List<PhoneticInfo> getList() {  //返回列表格式的数据，即得到list
        return phoneticInfoList;
    }


    public void setList(List<PhoneticInfo> list) {
        this.phoneticInfoList = list;
    }
}
