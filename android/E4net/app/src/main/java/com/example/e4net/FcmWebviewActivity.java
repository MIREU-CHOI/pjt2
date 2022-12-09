package com.example.e4net;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FcmWebviewActivity extends AppCompatActivity {

    WebView wv_fcm;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm_webview);

        sharedPreferences = getSharedPreferences("e4_default", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        wv_fcm = findViewById(R.id.wv_fcm);

        wv_fcm.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("app title")
                        .setMessage("app" + message)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("[webview]", "확인버튼 클릭");
                                result.confirm();
                            }
                        })
                        .setCancelable(false)
                        .create().show();
                //return super.onJsAlert(view, url, message, result);
                return true;
            }
        });

        wv_fcm.setWebViewClient(new WebViewClient(){
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

        WebSettings webSettings = wv_fcm.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);

        wv_fcm.loadUrl(StaticFinalLabelsClass.SERVER_IP_ADDRESS+"/android/fcm");
        wv_fcm.addJavascriptInterface(new JsInterface
                (this, wv_fcm), "android");
                // (Activity, WebView)
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wv_fcm.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wv_fcm.canGoBack()){
            wv_fcm.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}