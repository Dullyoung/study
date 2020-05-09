package com.example.study.control.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.study.control.fragments.Fragment_Learn.Fragment_Learn_Child;
import com.example.study.control.fragments.Fragment_index.Fragment_Main_Index;
import com.example.study.control.fragments.Fragment_Learn.Fragment_Learn;
import com.example.study.control.fragments.Fragment_Phonics.Fragment_Phonics;
import com.example.study.control.fragments.Fragment_Read_To_Me.Fragment_Read_To_Me;
import com.example.study.model.Cache.CacheUtils;
import com.example.study.model.bean.AdvInfoBean;
import com.example.study.model.bean.GoodListInfo;
import com.example.study.model.bean.LearnInfo;
import com.example.study.model.bean.LearnInfoWrapper;
import com.example.study.model.bean.LoginDataInfo;
import com.example.study.model.engin.AdvEngine;
import com.example.study.model.engin.GoodEngin;
import com.example.study.model.engin.InitEngin;
import com.example.study.model.engin.LearnEngin;
import com.example.study.view.adapter.FragAdapter;
import com.example.study.view.adapter.LearnAdapter;
import com.example.study.view.widget.CacheTipDialog;
import com.example.study.view.widget.Exit_Dialog;
import com.example.study.view.widget.JumpDialog;
import com.example.study.view.widget.MyUtils;
import com.example.study.view.widget.MyViewPager;
import com.example.study.view.widget.Pay_Dialog;
import com.example.study.R;
import com.example.study.view.widget.Share_Dialog;
import com.kk.securityhttp.domain.ResultInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.Observer;

import static com.example.study.model.Cache.Cache_Json.dir;
import static com.example.study.model.Cache.Cache_Json.readTextFile;
import static com.example.study.model.Cache.Cache_Json.saveToSDCard;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private LayoutInflater layoutInflater;
    public List<ImageView> images;
    private FragAdapter adapter;
    private MyViewPager viewPager;
    private RelativeLayout relativeLayout;

    ImageView setting;
    TextView h5page;
    TextView cache_size, sys_version;
    private int curindex = 0;//当前显示的页数
    String versionData;
    int SUCCESS = 666;
    int FAILED = 888;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == SUCCESS) {
                CheckVersion();
            }
            if (msg.what == FAILED) {
                Toast.makeText(MainActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // setFullScreenMode();
        // setFullScreen();
        hideSystemUI();
        //将imageview与对应的图片通过id绑定
        layoutInflater = LayoutInflater.from(this);//获取layout inflater实例
        h5page = findViewById(R.id.h5page);
        MyUtils.saveImageToSD( BitmapFactory.decodeResource(getResources(),R.mipmap.icon_share),dir.getAbsolutePath(),"shareIcon.png");

        GoToH5();
        SetView();
        getGoodInfo();
        getShareInfo();

        EventBus.getDefault().register(this);
        init();//主界面滑动
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void getShareInfo(){
        if (new File(dir,"shareInfo").exists()){
            return;
        }
        InitEngin initEngin=new InitEngin(this);
        initEngin.getLoginInfo().subscribe(new Observer<ResultInfo<LoginDataInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<LoginDataInfo> loginDataInfoResultInfo) {
                loginDataInfoResultInfo.setResponse(null);
                saveToSDCard(MainActivity.this,"shareInfo",JSONObject.toJSONString(loginDataInfoResultInfo));
            }
        });
    }

private void getGoodInfo(){
        if (new File(dir,"GoodInfo").exists()){
            return;
        }
    GoodEngin engin = new GoodEngin(this);
    engin.getGoodList().subscribe(new Observer<ResultInfo<GoodListInfo>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(ResultInfo<GoodListInfo> goodListInfoResultInfo) {
            goodListInfoResultInfo.setResponse(null);
            saveToSDCard(MainActivity.this, "GoodInfo", JSON.toJSONString(goodListInfoResultInfo));
        }
    });

};

    //H5界面数据获取
    public void GoToH5() {
        if (new File(dir,"advInfo").exists()){
            String json=  readTextFile("advInfo");
            Type type = new TypeReference<ResultInfo<AdvInfoBean>>() {
            }.getType();
            ResultInfo<AdvInfoBean> advInfo = JSONObject.parseObject(json, type);
            h5page.setText(advInfo.getData().getH5page().getButton_txt());
            Intent intent = new Intent(MainActivity.this, H5page.class);
            intent.putExtra("url", advInfo.getData().getH5page().getUrl());
            intent.putExtra("button_text", advInfo.getData().getH5page().getButton_txt());
            h5page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                    onPause();
                }
            });
            return;
        }


        AdvEngine engine = new AdvEngine(this);
        engine.getAdvInfo().subscribe(new Observer<ResultInfo<AdvInfoBean>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<AdvInfoBean> advInfoBeanResultInfo) {
                advInfoBeanResultInfo.setResponse(null);
                saveToSDCard(MainActivity.this,"advInfo", JSON.toJSONString(advInfoBeanResultInfo));
                h5page.setText(advInfoBeanResultInfo.getData().getH5page().getButton_txt());
                Intent intent = new Intent(MainActivity.this, H5page.class);
                intent.putExtra("url", advInfoBeanResultInfo.getData().getH5page().getUrl());
                intent.putExtra("button_text", advInfoBeanResultInfo.getData().getH5page().getButton_txt());
                h5page.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(intent);
                        onPause();
                    }
                });
            }
        });

    }


    public String getVersionName() throws Exception {
        // 获取package manager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    String TAG = "aaaa";

    //检查系统版本是不是最新
    private void CheckVersion() {
        String uri;
        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(versionData);
            uri = jsonObject.get("uri").toString();
            if (getVersionName().equals(jsonObject.get("version"))) {
                Toast.makeText(MainActivity.this, "已是最新版本！", Toast.LENGTH_SHORT).show();
            } else {
                Log.i(TAG, "version" + jsonObject.get("version"));
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("版本更新")
                        .setMessage("有新版本可以更新啦~")
                        .setPositiveButton("立即下载", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(uri));
                                startActivity(viewIntent);//一定要记得从apkName=后面这个位置也要修改包名
                            }
                        })
                        .setNegativeButton("取消", null).create().show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    CacheTipDialog cacheTipDialog;

    //缓存显示和系统版本显示
    private void setSystemAndCache() {
        findViewById(R.id.sys_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                versionData = getSystemVersionData();
            }
        });
        findViewById(R.id.cache_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cacheTipDialog == null) {
                    cacheTipDialog = new CacheTipDialog(MainActivity.this);
                    cacheTipDialog.setOnButtonClickListener(new CacheTipDialog.OnButtonClickListener() {
                        @Override
                        public void Sure() {
                            setCache_text();
                        }
                    });
                }
                cacheTipDialog.show();
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCache_text();
                findViewById(R.id.setting_layout).setVisibility(View.VISIBLE);
                //设置onTouch拦截触摸动作 避免点击穿透
                findViewById(R.id.setting_layout).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
            }
        });
    }

    //函数部分
    public void SetView() {
        cache_size = findViewById(R.id.cache_size);
        sys_version = findViewById(R.id.sys_version);
        images = new ArrayList<>(); //创建动态数组存ImageView 的对象
        images.add(findViewById(R.id.index));
        images.add(findViewById(R.id.learn));
        images.add(findViewById(R.id.read_to_me));
        images.add(findViewById(R.id.phonics));
        images.add(findViewById(R.id.pay_main));
        images.add(findViewById(R.id.share));
        setting = findViewById(R.id.setting);

        setSystemAndCache();
        for (int i = 0; i < images.size(); i++) {
            images.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findViewById(R.id.setting_layout).setVisibility(View.GONE);
                    switch (v.getId()) {
                        case R.id.pay_main:
                            PayIn(v);
                            break;
                        case R.id.index:
                            switchTab(0, false);
                            break;
                        case R.id.learn:
                            switchTab(1, false);
                            break;
                        case R.id.read_to_me:
                            switchTab(2, false);
                            break;
                        case R.id.phonics:
                            switchTab(3, false);
                            break;
                        case R.id.share:
                            if (viewPager.getCurrentItem() == 1 || viewPager.getCurrentItem() == 2) {
                                Jump();
                            } else {
                                Shared();
                            }

                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }

    JumpDialog jumpDialog = null;
    List<String> url;

    private void Jump() {
        if (url == null) {
            url = new ArrayList<>();
            LearnInfoWrapper learnInfoWrapper = getLearnInfoWrapper();
            for (LearnInfo l : learnInfoWrapper.getLearnInfoList()
            ) {
                url.add(l.getImg());
            }
        }
        if (jumpDialog == null) {
            jumpDialog = new JumpDialog(MainActivity.this);
            jumpDialog.setRv(url);
        }
        jumpDialog.show();
    }

    LearnInfoWrapper learnInfoWrapper;

    private LearnInfoWrapper getLearnInfoWrapper() {
        if (new File(dir, "LearnJson").exists()) {
            String ResultLocal = readTextFile("LearnJson");//本地获取到的Json数据
            Type type = new TypeReference<ResultInfo<LearnInfoWrapper>>() {
            }.getType();
            ResultInfo<LearnInfoWrapper> json = com.alibaba.fastjson.JSONObject.parseObject(ResultLocal, type);
            learnInfoWrapper = json.getData();

        } else {
            LearnEngin engin = new LearnEngin(this);
            engin.getLearnInfo().subscribe(new Observer<ResultInfo<LearnInfoWrapper>>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(ResultInfo<LearnInfoWrapper> learnInfoWrapperResultInfo) {
                    if (!new File(dir, "LearnJson").exists()) {
                        learnInfoWrapperResultInfo.setResponse(null);
                        saveToSDCard(MainActivity.this, "LearnJson", JSON.toJSONString(learnInfoWrapperResultInfo));
                    }
                    learnInfoWrapper = learnInfoWrapperResultInfo.getData();
                }
            });
        }
        return learnInfoWrapper;
    }

    //设置缓存数据
    private void setCache_text() {
        try {
            sys_version.setText(getVersionName());
            cache_size.setText(CacheUtils.getTotalCacheSize(MainActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "获取缓存大小失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        viewPager = findViewById(R.id.viewpager);

        adapter = new FragAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);//设置自定义的adapter适配器
        viewPager.setCurrentItem(curindex);//设置默认页码
        viewPager.setOffscreenPageLimit(4);//设置最大缓冲页数
        viewPager.addOnPageChangeListener(this);//设置滑动监听
    }

    //取消所有选中效果
    public void CancelAllSelect() {
        images.get(0).setImageResource(R.mipmap.main_index);
        images.get(1).setImageResource(R.mipmap.main_learn);
        images.get(2).setImageResource(R.mipmap.main_read_to_me);
        images.get(3).setImageResource(R.mipmap.main_phonics);
    }

    //点击效果
    public void switchTab(int position, boolean isPage) {
        curindex = viewPager.getCurrentItem();
        if (!isPage && position == curindex) {
            return;//如果页面可以滑动并且标题位置和当前页页面一致 则返回不进行任何操作。
        }
        CancelAllSelect(); //取消所有选中效果
        ImageView imageView = images.get(position);
        switch (position) {//获得当前位置信息，将对应的页面标题改为选中状态
            case 0:
                imageView.setImageResource(R.mipmap.main_index_selected);
                break;
            case 1:
                imageView.setImageResource(R.mipmap.main_learn_selected);
                break;
            case 2:
                imageView.setImageResource(R.mipmap.main_read_to_me_selected);
                break;
            case 3:
                imageView.setImageResource(R.mipmap.main_phonics_selected);
                break;
            default:
                break;
        }
        viewPager.setCurrentItem(position);//将viewpager跳转到点击的页面

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(String s) { //跟我读界面的权限请求 因为请求权限弹窗只能拿到activity里来进行
        if (s.equals("permission")) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }
        if (s.equals("pay")) {
            PayIn(null);
        }
    }

    Exit_Dialog myDialog;

    //捕捉用户返回退出
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (Jzvd.backPress()) {
                return true;
            }
            if (myDialog == null) {
                myDialog = new Exit_Dialog(this, "真的要离开我吗？");
            }
            myDialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    Pay_Dialog pay_main;

    //支付窗口弹窗
    public void PayIn(View v) {

        if (pay_main == null) {
            pay_main = new Pay_Dialog(MainActivity.this);
            pay_main.setCanceledOnTouchOutside(false);
            Window window = pay_main.getWindow();
            window.setWindowAnimations(R.style.dialog_action);
        }
        pay_main.show();
    }

    Share_Dialog share;

    //分享按钮弹窗
    public void Shared() {


        if (share == null) {
            share = new Share_Dialog(this);
            share.setCanceledOnTouchOutside(false);//设置点击窗口外不可取消
            Window window = share.getWindow();
            window.setWindowAnimations(R.style.dialog_action);//设置从底部退出进入效果
        }
        share.show();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 1 || position == 2) {
            ((ImageView) findViewById(R.id.share)).setBackground(getResources().getDrawable(R.drawable.phonogram_bg));
        } else {
            ((ImageView) findViewById(R.id.share)).setBackground(getResources().getDrawable(R.drawable.share_bg));
        }
        switchTab(position, true);
        String StopMusic = "stopMusic";
        EventBus.getDefault().post(StopMusic);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
        EventBus.getDefault().post("stopVoice");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();
        EventBus.getDefault().unregister(this);
    }

    //获取系统版本信息 这里从自己搭建的服务器模拟
    private String getSystemVersionData() {
        String TAG = "OKHTTP";
        String url = getResources().getString(R.string.serverUrl);
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendRequestWithOkhttp(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        handler.sendEmptyMessage(FAILED);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //response用过一次之后流就会关闭 再次调用就会报状态异常的错误
                        versionData = response.body().string();
                        handler.sendEmptyMessage(SUCCESS);
                    }
                });
            }
        }).start();

        return versionData;
    }

    //带回调 对获取成功和失败有监控
    public void sendRequestWithOkhttp(String url, okhttp3.Callback callback) {
        RequestBody requestBody = RequestBody.create(JSON1, JSONObject.toJSON("{\"name\":\"admin\",\"psw\":\"admin\"}").toString());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    private static final MediaType JSON1 = MediaType.parse("application/json; charset=utf-8");
}