package com.example.e4net;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewMainActivity extends AppCompatActivity {

    WebView webviewMain;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_main);

        webviewMain = findViewById(R.id.webviewMain);

        sharedPreferences = getSharedPreferences("push", MODE_PRIVATE);
        editor = sharedPreferences.edit();

//        webviewMain.setWebChromeClient(new WebChromeClient(){
//            @Override
//            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
//                return super.onJsAlert(view, url, message, result);
//            }
//        });

        webviewMain.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d("[webview]", "shouldOverrideUrlLoading, url="+request.getUrl());
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("[webview]", "onPageStarted, url="+url);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("[webview]", "onPageFinished, url="+url);
                super.onPageFinished(view, url);
            }
        });

        WebSettings webSettings = webviewMain.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);

        webviewMain.loadUrl("http://192.168.10.138:8080/public/sample");
        webviewMain.addJavascriptInterface(new JsInterface
                (this, webviewMain, sharedPreferences, editor), "android");


    }
}