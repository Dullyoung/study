package com.example.study.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.file.FileNameGenerator;
import com.example.study.R;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {
    //	"id": 6, 这是每一页四个单词的相关信息
    //				"letter": "ee",
    //				"phonetic": "",
    //				"video": "http://phonetic.upkao.com/video/N4kNaD63tX.mp3",
    //				"word": "bee",
    //				"word_phonetic": "/bi:/"
    //下面是每一页的信息
    //				"id": 1,
    //			"img": "http://tic.upkao.com/Upload/Picture/1.png",
    //			"name": "/i:/",
    //			"video": "http://tic.upkao.com/Upload/video/1.mp4",
    //			"voice": "http://tic.upkao.com/Upload/mp3/1.mp3"
    MediaPlayer mediaPlayer;
    List<Integer> id;
    private List<String> letter;//需要变色的字母
    private List<String> video;//声音
    private List<String> word;//示例单词
    private String name;  //需要变色的音标
    private List<String> word_phonetic;//示例单词的音标
    Context context;
    HttpProxyCacheServer voiceProxy;
    Animation animation;
    MyVoiceNameGenerator generator;
    String word_text, phonetic_text;

    public void setConfig(Context context, List<Integer> id, List<String> letter, List<String> video, List<String> word, List<String> word_phonetic, String name) {
        this.context = context;
        this.id = id;
        this.letter = letter;
        this.video = video;
        this.word = word;
        this.word_phonetic = word_phonetic;
        this.name = name;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.word_layout, null);
        return new WordViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {


        if (animation == null) {
            animation = AnimationUtils.loadAnimation(context, R.anim.rotate_img);
            LinearInterpolator lin = new LinearInterpolator();
            animation.setInterpolator(lin);

        }


        holder.word.setText(word.get(position));


        holder.phonetic.setText(word_phonetic.get(position));


        holder.word_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBus.getDefault().post("stopMusic");
                generator = new MyVoiceNameGenerator();
                generator.position = position;
                voiceProxy = new HttpProxyCacheServer.Builder(context)
                        .maxCacheSize(1024 * 1024 * 500)  //设置最大缓存大小500m 单位kB 最大值为2g-1
                        .fileNameGenerator(generator) //缓存描述音频文件的名字
                        .build();
                //点击的时候 音频开始异步加载，同时 开启加载动画
                holder.loading.startAnimation(animation);
                holder.loading.setVisibility(View.VISIBLE);
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(voiceProxy.getProxyUrl(video.get(position)));
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            //音频加载完了之后 把加载动画关闭，然后开始播放音乐，再加载单词布局的伸缩动画
                            holder.loading.clearAnimation();
                            holder.loading.setVisibility(View.GONE);
                            mp.start();
                            holder.word_layout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.scanl));
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //对音频播放完成进行监听 释放内存 释放动画
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                            mp.reset();
                            mp.release();
                            mp=null;
                            holder.word_layout.clearAnimation();
                    }
                });

            }
        });

        //对示例单词 进行变红处理
        String str1 = word.get(position);
        String str2 = letter.get(position);
        int start = str1.indexOf(str2);
        int end = str1.lastIndexOf(str2);
        SpannableString stringWord = new SpannableString(str1);
        //在单词中第一次匹配到该字母，把它变红
        stringWord.setSpan(new ForegroundColorSpan(Color.RED), start, start + str2.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        //最后一次匹配到该字母  变红。 一般一个单词就最多匹配两次
        stringWord.setSpan(new ForegroundColorSpan(Color.RED), end, end + str2.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        holder.word.setText(stringWord);

        //对示例单词的音标进行变红处理

        str1 = word_phonetic.get(position);
        str2 = name.replace("/", "");//返回的音标带/ / 去掉
        start = str1.indexOf(str2);
        end = str1.lastIndexOf(str2);
        SpannableString stringPhonetic = new SpannableString(str1);
        stringPhonetic.setSpan(new ForegroundColorSpan(Color.RED), start, start + str2.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        stringPhonetic.setSpan(new ForegroundColorSpan(Color.RED), end, end + str2.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        holder.phonetic.setText(stringPhonetic);

    }

    @Override
    public void onViewRecycled(@NonNull WordViewHolder holder) {

        super.onViewRecycled(holder);
    }

    class MyVoiceNameGenerator implements FileNameGenerator {//音频文件缓存命名
        int position;

        public String generate(String url) {
            //    Uri uri = Uri.parse(url);
//        String videoId = uri.getQueryParameter("videoId");  //MD5加密视频的URL作为文件名
            //直接以文件名作为本地文件名 缓存的文件就是mp3文件
            return "desp_voice/" + word.get(position) + ".mp3";
        }
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {
        TextView word, phonetic;
        LinearLayout word_layout;
        ImageView loading;

        WordViewHolder(@NonNull View itemView) {
            super(itemView);
            word_layout = itemView.findViewById(R.id.word_layout);
            word = itemView.findViewById(R.id.word);
            phonetic = itemView.findViewById(R.id.phonetic);
            loading = itemView.findViewById(R.id.loading);

        }
    }


}
