package com.example.study.control.fragments.Fragment_Learn;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.file.FileNameGenerator;
import com.example.study.R;
import com.example.study.model.Cache.GlideApp;
import com.example.study.model.bean.LearnInfo;
import com.example.study.model.bean.LearnInfoExample;
import com.example.study.view.adapter.WordAdapter;
import com.example.study.view.widget.MyJzvd;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;


import static cn.jzvd.Jzvd.releaseAllVideos;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Learn_Child extends Fragment {

    private MyJzvd video;
    private MediaPlayer mediaPlayer;
    private TextView textView;
    private LearnInfo learnInfo;
    private ImageView desp_voice_img, imageView;
    private HttpProxyCacheServer videoProxy, despProxy;
    private String videoUrl, despUrl;
    List<Integer> id;
    private List<String> letter;//该音标对应的单词
    private List<String> video_voice;//示例单词的语音 key名叫video 其实是语音
    private List<String> word;//示例单词
    private List<String> word_phonetic;//单词的全部音标
    private String name;//要变红的音标
    RecyclerView word_rv;
    private RelativeLayout relativeLayout;
    View rootView;
    WordAdapter wordAdapter;
    String TAG="aaaa";
    public Fragment_Learn_Child(LearnInfo learnInfo) {
        // Required empty public constructor
        this.learnInfo = learnInfo;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_fragment_learn_child, null);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);//注册eventbus接消息
        }
        findview(rootView);//对象绑定控件
        setData();
        setCache();
        return rootView;

    }

    private void findview(View v) {
        imageView = v.findViewById(R.id.siv);
        video = v.findViewById(R.id.my_video_view);
        textView = v.findViewById(R.id.text_desp);
        relativeLayout = v.findViewById(R.id.video_bg);
        relativeLayout.setBackgroundResource(R.mipmap.ic_video_bg);
        desp_voice_img = v.findViewById(R.id.desp_voice_image);
        desp_voice_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDespPlayer();
            }
        });
        word_rv = v.findViewById(R.id.word_rv);
    }

    RequestOptions options;
    public void setData() {
        if (letter == null || video_voice == null || id == null || word == null || word_phonetic == null) {
            letter = new ArrayList<>();
            video_voice = new ArrayList<>();
            id = new ArrayList<>();
            word = new ArrayList<>();
            word_phonetic = new ArrayList<>();
            wordAdapter = new WordAdapter();
            options = new RequestOptions();
        }
        for (LearnInfoExample l : learnInfo.getLearnInfoExampleList()) {
            id.add(l.getId());
            word.add(l.getWord().trim());
            word_phonetic.add(l.getWord_phonetic().trim());
            letter.add(l.getLetter().trim());
            video_voice.add(l.getVideo());
            wordAdapter.setConfig(getContext(), id, letter, video_voice, word, word_phonetic, learnInfo.getName());
            word_rv.setAdapter(wordAdapter);
        }
        if (options==null){
            options.placeholder(R.mipmap.ic_player_error);
            options.error(R.mipmap.ic_player_error);
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            options.skipMemoryCache(true);
        }
        GlideApp.with(Fragment_Learn_Child.this).load(learnInfo.getImg()).apply(options).thumbnail(0.1f).into(imageView);
        word_rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        textView.setText(learnInfo.getDesp());

    }

    private void setDespPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(despUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } else {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                resetPlayer();
            }
        });

    }

    private void setCache() {
        if (videoProxy == null) {
            videoProxy = new HttpProxyCacheServer.Builder(getContext())
                    .maxCacheSize(1024 * 1024 * 500)  //设置最大缓存大小500m 单位kB 最大值为2g-1
                    .fileNameGenerator(new MyVideoFileNameGenerator()) //缓存小窗口视频文件的名字
                    .build();
        }
        if (despProxy == null) {
            despProxy = new HttpProxyCacheServer.Builder(getContext())
                    .maxCacheSize(1024 * 1024 * 500)  //设置最大缓存大小500m 单位kB 最大值为2g-1
                    .fileNameGenerator(new MyDespFileNameGenerator()) //缓存描述音频文件的名字
                    .build();
        }
        despUrl = despProxy.getProxyUrl(learnInfo.getDesp_audio());
        videoUrl = videoProxy.getProxyUrl(learnInfo.getVideo());
        video.setUp(videoUrl, "");
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;//设置视频默认横屏 不然会导致切换到竖屏
        video.setAllControlsVisiblity(View.GONE, View.GONE, View.VISIBLE, View.GONE, View.VISIBLE, View.GONE, View.GONE);
        GlideApp.with(Fragment_Learn_Child.this).load(learnInfo.getCover()).thumbnail(0.1f).apply(options).into(video.posterImageView);

    }


    private class MyDespFileNameGenerator implements FileNameGenerator {//音频文件缓存命名

        public String generate(String url) {
            //    Uri uri = Uri.parse(url);
//        String videoId = uri.getQueryParameter("videoId");  //MD5加密视频的URL作为文件名
            //直接以文件名作为本地文件名 缓存的文件就是mp3文件
            return "desp_voice/" + learnInfo.getId() + ".mp3";
        }
    }

    private class MyVideoFileNameGenerator implements FileNameGenerator {//视频文件缓存命名

        @Override
        public String generate(String url) {
            return "Learn/" + learnInfo.getId() + ".mp4";
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void everythings(String s) {
        //函数名可以随意写  但是接受的数据必须是是在另一个fragment里Post的数据类型
        if (s == "stopMusic" && mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }


    public void resetPlayer()//释放
    {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    public void onDestroyView() {
        releaseAllVideos();//释放视频的内存
        resetPlayer();//释放音频的内存
        EventBus.getDefault().unregister(this);//Fragment销毁时解绑
        super.onDestroyView();
    }


}
