package com.example.study.control.fragments.Fragment_Read_To_Me;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.TypeReference;
import com.danikula.videocache.HttpProxyCacheServer;
import com.danikula.videocache.file.FileNameGenerator;
import com.example.study.model.bean.LearnInfoWrapper;
import com.example.study.model.bean.PageChanger;
import com.example.study.model.engin.LearnEngin;
import com.example.study.R;
import com.example.study.view.adapter.ReadToMeAdapter;
import com.kk.securityhttp.domain.ResultInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import rx.Observer;

import static com.example.study.model.Cache.Cache_Json.dir;
import static com.example.study.model.Cache.Cache_Json.readTextFile;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Read_To_Me extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private Boolean playing = false;
    private ViewPager imgvp;
    private ReadToMeAdapter fragAdapter;
    private int curindex;
    private int position;
    private TextView textView;//页码
    private ProgressBar progress;
    private ImageView last, next, playbtn,tip_img;//各种按钮
    private LearnEngin learnEngin;
    private AssetManager assetManager;
    private MediaPlayer tip, first, repeat, recordPlayer;
    private MediaRecorder mediaRecorder;
    private int repeat_count = 0;
    private TextView repeat_text ;
    LinearLayout tip_layout;
    private ResultInfo<LearnInfoWrapper> jsonData;
    View rootView;
    int time=0;
    String VoicePath=Environment.getExternalStorageDirectory().getPath()+"/Android/data/com.example.study/cache/record";
    public Fragment_Read_To_Me() {

        // Required empty public constructor
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                tip_layout.setVisibility(View.VISIBLE);
                progress.setProgress(100*(3-time)/3);
                time++;
                if (time==4){
                    tip_layout.setVisibility(View.GONE);
                    time=0;
                    return;
                }
                handler.sendEmptyMessageDelayed(1,1000);
            }
            if (msg.what==2){
                //隐藏倒计时
                tip_layout.setVisibility(View.GONE);
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fragment_readtome, null);
        EventBus.getDefault().register(this);
        last = rootView.findViewById(R.id.lastbtn);
        next = rootView.findViewById(R.id.nextbtn);
        tip_layout=rootView.findViewById(R.id.tip_text);
        playbtn = rootView.findViewById(R.id.playbtn);
        progress=rootView.findViewById(R.id.progress_bar);
        playbtn.setBackgroundResource(R.mipmap.read_stop_normal_icon);
        imgvp = rootView.findViewById(R.id.imgvp);
        textView = rootView.findViewById(R.id.pageshow);
        repeat_text = rootView.findViewById(R.id.repeat_count);
        tip_img=rootView.findViewById(R.id.tip_img);
        tip_img.setBackgroundResource(R.mipmap.splash_bg1);
        repeat_text.setText("跟读进度:" + repeat_count + "/" + "3");
        last.setOnClickListener(this);
        next.setOnClickListener(this);
        playbtn.setOnClickListener(this);
        assetManager = getActivity().getAssets();
        if (new File(dir, "LearnJson").exists()) {
            String ResultLocal = readTextFile("LearnJson");//本地获取到的Json数据
            Type type = new TypeReference<ResultInfo<LearnInfoWrapper>>() {
            }.getType();
            ResultInfo<LearnInfoWrapper> json = com.alibaba.fastjson.JSONObject.parseObject(ResultLocal, type);
            jsonData = json;
            fragAdapter = new ReadToMeAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,json);//获取适配器
            imgvp.setAdapter(fragAdapter);//设置适配器
            imgvp.setCurrentItem(0);
            imgvp.setOffscreenPageLimit(1);
            imgvp.addOnPageChangeListener(Fragment_Read_To_Me.this);
        } else {
            getNetData();//获取网络数据
        }

        return rootView;
    }



    public void getNetData() {
        learnEngin = new LearnEngin(getActivity());
        learnEngin.getLearnInfo().subscribe(new Observer<ResultInfo<LearnInfoWrapper>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<LearnInfoWrapper> learnInfoWrapperResultInfo) {
                if (learnInfoWrapperResultInfo != null && learnInfoWrapperResultInfo.getData() != null) {
                    jsonData = learnInfoWrapperResultInfo;
                    fragAdapter = new ReadToMeAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                            learnInfoWrapperResultInfo);//获取适配器
                    imgvp.setAdapter(fragAdapter);//设置适配器
                    imgvp.setCurrentItem(0);
                    imgvp.setOffscreenPageLimit(1);
                    imgvp.addOnPageChangeListener(Fragment_Read_To_Me.this);
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        position = imgvp.getCurrentItem();
        switch (v.getId()) {
            case R.id.lastbtn:
                Last(v, position);
                break;
            case R.id.nextbtn:
                Next(v, position);
                break;
            case R.id.playbtn:
                //每次点击的时候检查一下权限 如果没有录音权限 就post一个消息到activity里弹窗，
                // 在fragment里弹窗不会显示，Fragment没有windowsManager对象不能操作弹窗
                if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.RECORD_AUDIO )== PackageManager.PERMISSION_GRANTED){
                    if (playing) {
                        playing = false;
                        handler.sendEmptyMessage(2);
                        tip_img.setBackgroundResource(R.mipmap.splash_bg1);
                        ReleaseAll();
                    } else {
                       playbtn.setBackgroundResource(R.drawable.play_btn_bg);
                        StartPlay();
                        repeat_count = 0;
                        playing = true;
                    }
                }else {
                    EventBus.getDefault().post("permission");
                }
                break;

        }
    }

    private void ReleaseAll() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        if (tip != null) {
            tip.stop();
            tip.release();
            tip = null;
        }
        if (first != null) {
            first.stop();
            first.release();
            first = null;
        }
        if (repeat != null) {
            repeat.stop();
            repeat.release();
            repeat = null;
        }
        playbtn.setBackgroundResource(R.drawable.stop_btn_bg);
        if (recordPlayer != null) {
            recordPlayer.stop();
            recordPlayer.release();
            recordPlayer = null;
        }
        if (standardVoice!=null){
            standardVoice.stop();
            standardVoice.release();
            standardVoice=null;
        }
        repeat_count = 0;
        repeat_text.setText("跟读进度:" + repeat_count + "/3");

    }

    private void PrepareTips() {
        tip = new MediaPlayer();
        try {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("user_tape_tips.mp3");
            tip.reset();
            tip.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            tip.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void PrepareFirst() {
        first = new MediaPlayer();
        try {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("guide_01.mp3");
            first.reset();
            first.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            first.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void PrepareRepeat() {
        repeat = new MediaPlayer();
        try {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("guide_02.mp3");
            repeat.reset();
            //把Assets下的文件作为音源文件赋给MediaPlayer
            repeat.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            repeat.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void RecordTools() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        File file = new File(VoicePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        mediaRecorder.setOutputFile(VoicePath+"/voice.3gp");
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            tip_img.setBackgroundResource(R.mipmap.user_tape_icon);
            playbtn.setBackgroundResource(R.mipmap.reading_icon);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "录音失败，请检查麦克风或读写权限！", Toast.LENGTH_SHORT).show();
        }
    }



    //播放用户的录音
    private void StartRecordPlayer() {
        recordPlayer = new MediaPlayer();
        try {
            recordPlayer.setDataSource( VoicePath+"/voice.3gp");
            recordPlayer.prepare();
            recordPlayer.start();
            recordPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //统计次数 用户录音前两次播放完放“请跟我一起再读一遍提示音”，
                    if (repeat_count == 1 || repeat_count == 2) {
                        repeat.start();
                        playbtn.setBackgroundResource(R.drawable.play_btn_bg);
                    }
                //第三次结束。
                    if (repeat_count == 3) {
                        repeat_count = 0;
                        playing = false;
                        playbtn.setBackgroundResource(R.drawable.stop_btn_bg);
                        repeat_text.setText("跟读进度:" + repeat_count + "/" + "3");
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "打开录音文件失败", Toast.LENGTH_SHORT).show();
        }

    }

    private String TAG = "aaaa";

    private void StartPlay() {
        PrepareTips();
        PrepareFirst();
        PrepareRepeat();
        first.start();
        //播放第一次引导语 然后播放标准读音
        //播放完成播放提示音
        first.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playbtn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        StandardVoice();
                        repeat_count++;
                        repeat_text.setText("跟读进度:" + repeat_count + "/" + "3");
                    }
                }, 500);
            }
        });

        //提示音播放完准备录音
        tip.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                RecordTools();//开始录音 打开文字提示 发送消息到主线程进行视图更新
                handler.sendEmptyMessage(1);
                playbtn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //延时之后 结束录音
                        if (mediaRecorder != null) {
                            mediaRecorder.stop();
                            mediaRecorder.release();
                            mediaRecorder = null;
                        }
                        //隐藏文字提示
                        tip_img.setBackgroundResource(R.mipmap.splash_bg1);

                         //播放录音文件
                        StartRecordPlayer();
                    }
                }, 3000);//延时三秒后停止录制 释放录音机
            }
        });

        repeat.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playbtn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //重复第一次和重复第二次的时候播放
                        if (repeat_count == 2 || repeat_count == 1) {
                            StandardVoice();
                            repeat_count++;
                            repeat_text.setText("跟读进度:" + repeat_count + "/" + "3");
                        }
                    }
                }, 100);

            }
        });

    }

    private class MyVoiceFile implements FileNameGenerator {

        @Override
        public String generate(String url) {
            return "/voice/"+jsonData.getData().getLearnInfoList().get(imgvp.getCurrentItem()).getId() + ".mp3";
        }
    }

    private HttpProxyCacheServer cacheServer;
    private MediaPlayer standardVoice;

    private void StandardVoice() {
        standardVoice = new MediaPlayer();
        standardVoice.setAudioStreamType(AudioManager.STREAM_MUSIC);
        if (cacheServer == null) {
            cacheServer = new HttpProxyCacheServer.Builder(getContext()).maxCacheSize(1024 * 1024 * 500)
                    .fileNameGenerator(new MyVoiceFile())
                    .build();
        }
        String uri = cacheServer.getProxyUrl(jsonData.getData().getLearnInfoList().get(imgvp.getCurrentItem()).getVoice());
        try {
            standardVoice.setDataSource(uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        standardVoice.prepareAsync();
        standardVoice.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                standardVoice.start();
            }
        });
//监测播放完成 播放用户的读音
        standardVoice.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                tip.start();//提示音之后就是播放用户录音
            }
        });
       }

    private void Last(View view, int position) {
        imgvp.setCurrentItem(curindex);
        setPage();
    }

    private void Next(View view, int position) {
        curindex = position + 1;
        imgvp.setCurrentItem(curindex);
        setPage();
    }

    public void setPage() {

        String pageInfo = (imgvp.getCurrentItem() + 1) + "/" + fragAdapter.getCount();
        textView.setText(pageInfo);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { //页面切换时会调用此方法。
        curindex = position - 1;
        if (curindex == -1) {
            next.setVisibility(View.VISIBLE);
            last.setVisibility(View.INVISIBLE);
        } else if (curindex == fragAdapter.getCount() - 2) {
            last.setVisibility(View.VISIBLE);
            next.setVisibility(View.INVISIBLE);
        } else {
            last.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
        }
        ReleaseAll();
        setPage();//页面切换时更换底部页脚
    }


    @Override
    public void onPageSelected(int position) {
        playing=false;
        EventBus.getDefault().post(new PageChanger("read", position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ChangeRead(PageChanger pageChanger) {
        if (pageChanger.getString() == "learn")
            imgvp.setCurrentItem(pageChanger.getPosition());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(String s){
        if (s.equals("stopMusic")||s.equals("stopVoice")){
            ReleaseAll();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ReleaseAll();
        EventBus.getDefault().unregister(this);
    }


}
