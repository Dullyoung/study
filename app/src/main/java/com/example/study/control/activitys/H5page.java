package com.example.study.control.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.study.R;

public class H5page extends AppCompatActivity {
    private String url;
    private WebView webView;
    String TAG = "h5";
    String title;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_h5page);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("button_text");
        ((TextView) findViewById(R.id.title)).setText(title);
        webView = findViewById(R.id.myh5);
        linearLayout = findViewById(R.id.errorpage);
        initWebView();
        findViewById(R.id.back).setOnClickListener(v -> finish());
    }

    private void initWebView() {
        webView.setWebViewClient(new MyWebViewClient());
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);//允许JS运行
        webView.loadUrl(url);//h5界面网址
    }


    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,
                                                String url_Turntable) {
            view.loadUrl(url_Turntable);
            return true;
        }

        String TAG = "aaaa";

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {//整个界面请求失败时回调

            webView.setVisibility(View.GONE);//h5界面隐藏
            linearLayout.setVisibility(View.VISIBLE);//加载失败时显示加载失败的界面
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {//h5界面部分内容无法获取时调用

            linearLayout.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {

            webView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            linearLayout.setVisibility(View.GONE);//开始加载时隐藏错误界面
            webView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {  //检测用户界面内返回
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() { //用户主界面退出
        finish();
    }

    public void reload(View view) {
        webView.loadUrl(url);
    }

}

