package com.example.study.model.bean;

import java.io.Serializable;

public class PhonticDetailBean implements Serializable {
    //{
    //	"code": 1,
    //	"data": {
    //		"id": "201",
    //		"title": "第一节：音标简介",
    //		"pid": "969",
    //		"img": "https:\/\/yb.bshu.com\/uploads\/20180824\/759cffe49982aca7e5916d1d61297d26.png",
    //		"url": "http:\/\/voice.wk2.com\/video\/2017091801.mp4",
    //		"type_id": "8",
    //		"flag": "0",
    //		"user_num": "1009",
    //		"sort": "1",
    //		"add_time": "1504928384",
    //		"desp": "本节课程主要讲了音标的分类，音标发音部位。了解音标的基础知识，学起来才更容易！",
    //		"is_vip": "0",
    //		"original_price": "189",
    //		"current_price": "0"
    //	},
    //	"msg": "成功"
    //}
    private String title;
    private String img;
    private String url;
    private String desp;
    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
