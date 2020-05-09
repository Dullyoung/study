package com.example.study.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;



public class LoginDataInfo {


    @JSONField(name = "share_info")
    private ShareInfo shareInfo;

    public ShareInfo getShareInfo() {
        return shareInfo;
    }

    public void setShareInfo(ShareInfo shareInfo) {
        this.shareInfo = shareInfo;
    }




}
