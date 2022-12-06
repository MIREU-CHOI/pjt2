package com.example.e4net;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class IamportActivity extends AppCompatActivity {

    private WebView wv_iamport;
    private ProgressBar pb_iamport;

    class MyJavaScriptInterface2 {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processDATA(String data) {
            Bundle extra = new Bundle();
            Intent intent = new Intent();
            extra.putString("data", data);
            intent.putExtras(extra);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iamport);

        wv_iamport = findViewById(R.id.wv_iamport);
        pb_iamport = findViewById(R.id.pb_iamport);

        wv_iamport.getSettings().setJavaScriptEnabled(true);
        wv_iamport.getSettings().setDomStorageEnabled(true);

        wv_iamport.addJavascriptInterface(new MyJavaScriptInterface2(), "Android");

        wv_iamport.setWebViewClient(new WebViewClient(){
            // SSL 인증서 무시하는 코드
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                Log.d("[iamport]", "onReceivedSslError: 실패, \n error: "+error);
                handler.proceed();
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d("[iamport]", "shouldOverrideUrlLoading: \n request 메소드 => "+request.getMethod());
//                boolean res = super.shouldOverrideUrlLoading(view, request);
                view.loadUrl(request.getUrl().toString());
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("[iamport]", "onPageStarted: \n url => "+url);
                pb_iamport.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("[iamport]", "onPageFinished: \n url => "+url);
                pb_iamport.setVisibility(View.GONE);
//                wv_iamport.loadUrl("javascript:iamportFunc();");
            }
        });

        //ssl 인증이 없는 경우 해결을 위한 부분
        wv_iamport.setWebChromeClient(new WebChromeClient() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.d("[iamport]", "onPermissionRequest: \n request => "+request.getResources().toString());
                request.grant(request.getResources());
            }
        });
//        wv_iamport.loadUrl("http://192.168.10.138:8888/android/iamport");
        wv_iamport.loadUrl("javascript:iamportFunc();");
//        wv_iamport.loadUrl("file:///android_asset/www/iamport.html");








    }
}