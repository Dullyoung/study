package com.example.study.control.fragments.Fragment_Phonics;


import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.file.FileNameGenerator;
import com.example.study.R;
import com.example.study.model.Cache.GlideApp;
import com.example.study.model.bean.PhonticDetailBean;
import com.example.study.model.engin.PhoneticDetailEngin;
import com.kk.securityhttp.domain.ResultInfo;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.reflect.Type;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import rx.Observer;

import static cn.jzvd.Jzvd.releaseAllVideos;
import static com.example.study.model.Cache.Cache_Json.dir;
import static com.example.study.model.Cache.Cache_Json.readTextFile;
import static com.example.study.model.Cache.Cache_Json.saveToSDCard;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPhonetic_Info extends Fragment {
    private JzvdStd videoView;
    private ImageView back;
    private TextView title_tv, desp_tv;
    private RelativeLayout full_layout;
    private ViewGroup last;
    private String id;
    private HttpProxyCacheServer proxyCacheServer;
    View rootView;
    public FragmentPhonetic_Info(String id) {
        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_fragment_phonetic__info, null);
        setView(rootView);
        setData();
        //如果本地有数据就从本地获取
        if (new File(dir, "PhoneticDetail" + id).exists()) {
            String ResultLocal = readTextFile("PhoneticDetail" + id);//本地获取到的Json数据
            Type type = new TypeReference<ResultInfo<PhonticDetailBean>>() {
            }.getType();
            ResultInfo<PhonticDetailBean> json = JSONObject.parseObject(ResultLocal, type);
            if (json != null) {
                Log.i("aaaa", "onCreateView: "+json);
                title_tv.setText(json.getData().getTitle());
                desp_tv.setText(json.getData().getDesp());
                String proxyUrl = proxyCacheServer.getProxyUrl(json.getData().getUrl());
                RequestOptions options= new RequestOptions();
                options.placeholder(R.mipmap.ic_player_error);
                options.error(R.mipmap.ic_player_error);
                options.diskCacheStrategy(DiskCacheStrategy.ALL);
                options.skipMemoryCache(true);
                GlideApp.with(this).load(json.getData().getImg()).apply(options).thumbnail(0.1f).into( videoView.posterImageView);
                videoView.setUp(proxyUrl,json.getData().getTitle());
                videoView.setAllControlsVisiblity(View.GONE,View.GONE,View.VISIBLE,View.GONE,View.VISIBLE,View.GONE,View.GONE);
            } else {
                getNetData(id);
            }

        } else {
            //本地无数据再从网络获取
            getNetData(id);
        }

        return rootView;
    }



    private void setView(View view) {
        videoView = view.findViewById(R.id.my_video_view);
        back = view.findViewById(R.id.back_category_list_btn);
        title_tv = view.findViewById(R.id.phonetic_title);
        desp_tv = view.findViewById(R.id.phonetic_desp);
        Jzvd.NORMAL_ORIENTATION= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

    }

    private class MyVideoFileNameGenerator implements FileNameGenerator {//视频文件缓存命名

        @Override
        public String generate(String url) {
            return "Phonetic/" + id + ".mp4";
        }
    }

    private void setData() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releaseAllVideos();
                EventBus.getDefault().post("back");
            }
        });
        if (proxyCacheServer == null) {
            proxyCacheServer = new HttpProxyCacheServer.Builder(getContext())
                    .maxCacheSize(1024 * 1024 * 500)  //设置最大缓存大小500m 单位kB 最大值为2g-1
                    .fileNameGenerator(new MyVideoFileNameGenerator()) //缓存小窗口视频文件的名字
                    .build();
        }
    }

    private void getNetData(final String id) {
        PhoneticDetailEngin engin = new PhoneticDetailEngin(getContext());
        engin.getPhoneticDetailInfo(id).subscribe(new Observer<ResultInfo<PhonticDetailBean>>() {
            @Override
            public void onCompleted() {
                //数据获取完成时调用
            }

            @Override
            public void onError(Throwable e) {
                //出错时调用
            }

            @Override
            public void onNext(ResultInfo<PhonticDetailBean> detail) {
                if (!new File(dir, "PhoneticDetail" + id).exists()) { //如果文件不存在 就缓存
                    detail.setResponse(null);
                    saveToSDCard(getActivity(), "PhoneticDetail" + id, JSON.toJSONString(detail));
                }
                //获取数据并更改视图控件内容
                title_tv.setText(detail.getData().getTitle());
                desp_tv.setText(detail.getData().getDesp());
                String proxyUrl = proxyCacheServer.getProxyUrl(detail.getData().getUrl());
                RequestOptions options= new RequestOptions();
                options.placeholder(R.mipmap.ic_player_error);
                options.error(R.mipmap.ic_player_error);
                options.diskCacheStrategy(DiskCacheStrategy.ALL);
                options.skipMemoryCache(true);
                GlideApp.with(FragmentPhonetic_Info.this).load(detail.getData().getImg()).thumbnail(0.1f).apply(options).into( videoView.posterImageView);
                videoView.setUp(proxyUrl,detail.getData().getTitle());
                videoView.setAllControlsVisiblity(View.GONE,View.GONE,View.VISIBLE,View.GONE,View.VISIBLE,View.GONE,View.GONE);
            }
        });

    }

    @Override
    public void onDestroyView() {
        releaseAllVideos();
        System.gc();
        super.onDestroyView();

    }





}
