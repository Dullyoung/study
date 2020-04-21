package com.example.study.model.bean;

public class LearnInfoExample {
    int id;
    String word;
    String letter;
    String video;
    String word_phonetic;
    String phonetic;

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getWord_phonetic() {
        return word_phonetic;
    }

    public void setWord_phonetic(String word_phonetic) {
        this.word_phonetic = word_phonetic;
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


//"example":[{"id":"6","ptic_id":"1","phonetic":"","word":"bee","word_phonetic":"\/bi:\/","sort":"500","letter":"ee","video":"http:\/" +
//        "\/phonetic.upkao.com\/video\/N4kNaD63tX.mp3","app_id":"5"},{"id":"7","ptic_id":"1","phonetic":"","word":"see  ","word_phonetic":
//        "\/si:\/","sort":"500","letter":"ee  ","video":"http:\/\/phonetic.upkao.com\/video\/thiCefayQb.mp3","app_id":"5"},{"id":"8","ptic" +
//        "_id":"1","phonetic":"","word":"tree","word_phonetic":"\/tri:\/","sort":"500","letter":"ee","video":"http:\/\/phonetic.upkao.com\/" +
//        "video\/a88y5Kh7He.mp3","app_id":"5"},{"id":"9","ptic_id":"1","phonetic":"","word":"tea","word_phonetic":"\/ti:\/","sort":"500","l" +
//        "etter":"ea","video":"http:\/\/phonetic.upkao.com\/video\/4WmZ8fE8jR.mp3","app_id":"5"}]}