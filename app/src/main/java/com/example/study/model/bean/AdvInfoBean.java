package com.example.study.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class AdvInfoBean implements Serializable {
    @JSONField(name = "h5page")
    private H5page h5page;
    public H5page getH5page() {
        return h5page;
    }
    public void setH5page(H5page h5page) {
        this.h5page = h5page;
    }

    /*	"code": 1,
            "msg": "",
            "data": {
                "h5page": {
                    "id": "3",
                    "url": "http:\/\/m.upkao.com\/ybzs.html",
                    "img": "",
                    "statistics": "h5page",
                    "add_time": "1586508638",
                    "app_title": "音标app",
                    "apk_id": "1",
                    "button_txt": "音标知识"
                }
            }
        }*/
    public class H5page{
        String id;
        String url;
        String img;
        String statistics;
        String add_time;
        String app_title;
        String apk_id;
        String button_txt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getStatistics() {
            return statistics;
        }

        public void setStatistics(String statistics) {
            this.statistics = statistics;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getApp_title() {
            return app_title;
        }

        public void setApp_title(String app_title) {
            this.app_title = app_title;
        }

        public String getApk_id() {
            return apk_id;
        }

        public void setApk_id(String apk_id) {
            this.apk_id = apk_id;
        }

        public String getButton_txt() {
            return button_txt;
        }

        public void setButton_txt(String button_txt) {
            this.button_txt = button_txt;
        }
    }



}
