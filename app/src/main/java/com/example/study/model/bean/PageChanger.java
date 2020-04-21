package com.example.study.model.bean;

import java.io.Serializable;

public class PageChanger implements Serializable {
        private int position;
        private String string;

        public  PageChanger(String string,int position){
            this.string=string;
            this.position=position;
        }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}


