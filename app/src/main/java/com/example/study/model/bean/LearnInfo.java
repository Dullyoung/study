package com.example.study.model.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class LearnInfo {
    int id;
    String video;
    String voice;
    String cover;
    String img;
    String name;
    String desp;
    String desp_audio;
    String example;

@JSONField(name="example")
private List<LearnInfoExample> learnInfoExampleList;

    public List<LearnInfoExample> getLearnInfoExampleList() {
        return learnInfoExampleList;
    }

    public void setLearnInfoExampleList(List<LearnInfoExample> learnInfoExampleList) {
        this.learnInfoExampleList = learnInfoExampleList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getDesp_audio() {
        return desp_audio;
    }

    public void setDesp_audio(String desp_audio) {
        this.desp_audio = desp_audio;
    }
}

/*
2019-11-27 10:04:00.110 30491-30523/? I/securityhttp: 服务器返回数据->{"code":1,"data":{"list":[{"id":"1","video":"http:\/\/tic.upkao.com\/Upload\/video\/1.mp4",
        "voice":"http:\/\/tic.upkao.com\/Upload\/mp3\/1.mp3","cover":"http:\/\/tic.upkao.com\/Upload\/cover\/1.jpg","img":"http:\/\/tic.upkao.com\/Upload\/Picture\/" +
        "1.png","name":"\/i:\/","desp":"\u820c\u5c16\u62b5\u4e0b\u9f7f\uff0c\u820c\u524d\u90e8\u5c3d\u91cf\u5411\u786c\u989a\u62ac\u8d77\u3002\u5634\u5507\u5411\u4e24" +
        "\u65c1\u4f38\u5f00\u6210\u6241\u5e73\u578b\u3002\u6ce8\u610f\u4e00\u5b9a\u8981\u628a\u97f3\u53d1\u8db3\u3002","sort":"0","audio":null,"desp_audio":"http:\/\/p" +
        "honetic.upkao.com\/video\/ttS8dPyzKh.mp3","app_id":"5","image":null,"mouth_cover":null,"type":"","hanzi":"","strokes_img":"","phonetic":null,"example":[{"id":"6" +
        "","ptic_id":"1","phonetic":"","word":"bee","word_phonetic":"\/bi:\/","sort":"500","letter":"ee","video":"http:\/\/phonetic.upkao.com\/video\/N4kNaD63tX.mp3","app_" +
        "id":"5"},{"id":"7","ptic_id":"1","phonetic":"","word":"see  ","word_phonetic":"\/si:\/","sort":"500","letter":"ee  ","video":"http:\/\/phonetic.upkao.com\/video\/" +
        "thiCefayQb.mp3","app_id":"5"},{"id":"8","ptic_id":"1","phonetic":"","word":"tree","word_phonetic":"\/tri:\/","sort":"500","letter":"ee","video":"http:\/\/phonetic" +
        ".upkao.com\/video\/a88y5Kh7He.mp3","app_id":"5"},{"id":"9","ptic_id":"1","phonetic":"","word":"tea","word_phonetic":"\/ti:\/","sort":"500","letter":"ea","video":"h" +
        "ttp:\/\/phonetic.upkao.com\/video\/4WmZ8fE8jR.mp3","app_id":"5"}]},{"id":"2","video":"http:\/\/tic.upkao.com\/Upload\/video\/2.mp4","voice":"http:\/\/tic.upkao.com" +
        "\/Upload\/mp3\/2.mp3","cover":"http:\/\/tic.upkao.com\/Upload\/cover\/2.jpg","img":"http:\/\/tic.upkao.com\/Upload\/Picture\/2.png","name":"\/\u026a\/","desp":"\u820c" +
        "\u5c16\u62b5\u4e0b\u9f7f\uff0c\u820c\u524d\u90e8\u5411\u786c\u989a\u62ac\u8d77\u3002\u5634\u5507\u5411\u4e24\u65c1\u4f38\u5f00\u6210\u6241\u5e73\u578b\u3002\u53d1\u97f3" +
        "\u77ed\u4fc3\uff0c\u4e0a\u4e0b\u9f7f\u4e4b\u95f4\u53ef\u5bb9\u7eb3\u5c0f\u6307\u5c16\u3002","sort":"0","audio":null,"desp_audio":"http:\/\/phonetic.upkao.com\/video" +
        "\/m5tpwHTxh7.mp3","app_id":"5","image":null,"mouth_cover":null,"type":"","hanzi":"","strokes_img":"","phonetic":null,"example":[{"id":"10","ptic_id":"2","phonetic":
        "","word":"tip","word_phonetic":"\/t\u026ap\/","sort":"500","letter":"i","video":"http:\/\/phonetic.upkao.com\/video\/6xZ8Njeeyn.mp3","app_id":"5"},{"id":"11","ptic_" +
        "id":"2","phonetic":"","word":"pig  ","word_phonetic":"\/p\u026ag\/ ","sort":"500","letter":"i","video":"http:\/\/phonetic.upkao.com\/video\/tcP2abs2BN.mp3","app_id"
        :"5"},{"id":"12","ptic_id":"2","phonetic":"","word":"zip  ","word_phonetic":"\/z\u026ap\/","sort":"500","letter":"i","video":"http:\/\/phonetic.upkao.com\/video\/EnH" +
        "mKBd6k4.mp3","app_id":"5"},{"id":"13","ptic_id":"2","phonetic":"","word":"big","word_phonetic":"\/b\u026ag\/","sort":"500","letter":"i","video":"http:\/\/phonetic.u" +
        "pkao.com\/video\/44M57w37wX.mp3","app_id":"5"}]},{"id":"3","video":"http:\/\/tic.upkao.com\/Upload\/video\/3.mp4","voice":"http:\/\/tic.upkao.com\/Upload\/mp3\/3.mp" +
        "3","cover":"http:\/\/tic.upkao.com\/Upload\/cover\/3.jpg","img":"http:\/\/tic.upkao.com\/Upload\/Picture\/3.png","name":"\/e\/","desp":"\u820c\u5c16\u62b5\u4e0b\u9f7f" +
        "\uff0c\u820c\u524d\u90e8\u7a0d\u62ac\u8d77\uff0c\u6bd4\/i:\/\u4f4e\u3002\u4e0a\u4e0b\u9f7f\u95f4\u53ef\u5bb9\u7eb3\u4e00\u4e2a\u98df\u6307\u3002","sort":"0","audio"
        :null,"desp_audio":"http:\/\/phonetic.upkao.com\/video\/5bWtzPESmj.mp3","app_id":"5","image":null,"mouth_cover":null,"type":"","hanzi":"","strokes_img":"","phonetic"
        :null,"example":[{"id":"14","ptic_id":"3","phonetic":"","word":"bed ","word_phonetic":"\/bed\/","sort":"500","letter":"e","video":"http:\/\/phonetic.upkao.com\/video" +
        "\/TMfKep87bW.mp3","app_id":"5"},{"id":"15","ptic_id":"3","phonetic":"","word":"red  ","word_phonetic":"\/red\/","sort":"500","letter":"e","video":"http:\/\/phonetic" +
        ".upkao.com\/video\/KRYJhkMCjC.mp3","app_id":"5"},{"id":"16","ptic_id":"3","phonetic":"","word":"pet  ","word_phonetic":"\/pet\/","sort":"500","letter":"e","video":"" +
        "http:\/\/phonetic.upkao.
*/