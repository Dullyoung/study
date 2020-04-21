package com.example.study.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

public class PhoneticIndexBean implements Serializable {

    @JSONField(name = "yinbiao")
    private Spelling spelling;

    public Spelling getSpelling() {
        return spelling;
    }

    public void setSpelling(Spelling spelling) {
        this.spelling = spelling;
    }

    public class Spelling implements Serializable {

        private String desp;
        @JSONField(name = "detail")
        private List<Detail> detailList;

        public String getDesp() {
            return desp;
        }

        public void setDesp(String desp) {
            this.desp = desp;
        }

        public List<Detail> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<Detail> detailList) {
            this.detailList = detailList;
        }

        public  class Detail implements Serializable {
            //"id": "201",
            //					"title": "第一节：音标简介",
            //					"img": "https:\/\/yb.bshu.com\/uploads\/20180824\/759cffe49982aca7e5916d1d61297d26.png",
            //					"type_id": "8",
            //					"is_vip": "0",
            //					"icon": "http:\/\/tic.upkao.com\/Upload\/weikeimg\/category1.png"
            private String id;
            private String title;
            private String img;
            private String icon;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

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

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }
    }



    @JSONField(name = "pindu")
    private Symbol symbol;

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }



   public class Symbol implements Serializable {
        private String desp;

        public String getDesp() {
            return desp;
        }

        public void setDesp(String desp) {
            this.desp = desp;
        }

        public List<Detail> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<Detail> detailList) {
            this.detailList = detailList;
        }

        @JSONField(name = "detail")
        private List<Detail> detailList;

       public class Detail implements Serializable {


            private String id;
            private String title;
            private String img;
            private String icon;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

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

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }
    }
}
