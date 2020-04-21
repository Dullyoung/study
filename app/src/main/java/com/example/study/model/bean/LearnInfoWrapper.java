package com.example.study.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class LearnInfoWrapper  {
    @JSONField(name = "list")
    private List<LearnInfo> learnInfoList;

    public List<LearnInfo> getLearnInfoList() {
        return learnInfoList;
    }

    public void setLearnInfoList(List<LearnInfo> learnInfoList) {
        this.learnInfoList = learnInfoList;
    }
}
