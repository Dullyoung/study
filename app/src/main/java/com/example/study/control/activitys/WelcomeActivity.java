package com.example.study.control.activitys;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.example.study.R;
import com.example.study.model.bean.LoginDataInfo;
import com.example.study.model.engin.InitEngin;
import com.example.study.view.widget.Exit_Dialog;
import com.example.study.view.widget.MyUtils;
import com.kk.securityhttp.domain.ResultInfo;

import java.io.IOException;

import cn.jzvd.Jzvd;
import rx.Observer;

import static com.example.study.model.Cache.Cache_Json.dir;

public class WelcomeActivity extends AppCompatActivity {
    private ImageView imageView;
    private MediaPlayer mediaPlayer;
    private AnimationDrawable animationDrawable;
    public int time;//设置欢迎页时间
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0) {
                if (msg.arg1 == 0) {
                    textView.setText("跳过");
                } else {
                    textView.setText("跳过 " + msg.arg1);
                }
                if (time == 0) {
                    animationDrawable.stop();
                    GoPermission();
                    return;
                }
                time--;
                Message message = Message.obtain();
                message.what = 0;
                message.arg1 = time;
                handler.sendMessageDelayed(message, 1000);

            }
        }
    };
    private AssetManager assetManager;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        textView = findViewById(R.id.time);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoPermission();
            }
        });
        imageView = (ImageView) findViewById(R.id.splash);
        animationDrawable = (AnimationDrawable) imageView.getBackground();//获取逐帧动画对象
        animationDrawable.start();//启动动画
        //获取assetsManager 对象
        assetManager = getAssets();
        mediaPlayer = new MediaPlayer();
        try {
            //获取asset file descriptor 用 asset manager打开assets目录下的音频文件
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("splash.mp3");
            //重置音频播放器
            mediaPlayer.reset();
            //设置音频文件 三个参数 assetFileDescriptor getFileDescriptor startOffset和length
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            //开始准备
            mediaPlayer.prepare();
            //播放
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    time = (int) mediaPlayer.getDuration() / 1000;
                    mp.start();
                    mp.seekTo(1000);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            GoPermission();
            Toast.makeText(this, "资源加载失败", Toast.LENGTH_SHORT).show();
        }
        Message message = Message.obtain();
        message.what = 0;
        message.arg1 = time;
        handler.sendMessage(message);
    }

    private void GoPermission() {//跳转到权限页
        handler.removeMessages(0);
        Intent intent = new Intent(this, PermissionActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //摧毁时释放音乐播放器
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉用户返回退出
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Exit_Dialog myDialog = new Exit_Dialog(this, "真的要离开我吗？");
            myDialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}



