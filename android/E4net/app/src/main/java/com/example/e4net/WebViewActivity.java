package com.example.e4net;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private TextView tv_membId;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

//    @Override
//    protected void onStart() {
//        super.onStart();
//        sharedPreferences = getSharedPreferences("login_default", MODE_PRIVATE);
//        String id = sharedPreferences.getString("membId", "");
//        Log.d("[webview]", "onPageStarted, 회원id => "+id);
//        tv_membId.setText(id);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_web_view);

        sharedPreferences = getSharedPreferences("e4_default", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        webView = findViewById(R.id.webview);
//        tv_membId = findViewById(R.id.tv_membId);

        // setWeb "Chrome" Client  :  아직 안 쓴다 ?
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("app title")
                        .setMessage("app" + message)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("[webView]", "확인버튼 클릭");
                                result.confirm();
                            }
                        })
                        .setCancelable(false)
                        .create().show();
                //return super.onJsAlert(view, url, message, result);
                return true;
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                String id = sharedPreferences.getString("membId", "membId");
                Log.d("[webview]", "shouldOverrideUrlLoading, request => "+request.toString());
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                String id = sharedPreferences.getString("membId", "membId");
                Log.d("[webview]", "onPageStarted, url => "+url);
//                tv_membId.setText(id);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("[webview]", "onPageFinished, url="+url);
                super.onPageFinished(view, url);
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);

        // JavaScript 인터페이스 등록
        webView.addJavascriptInterface
                (new JsInterface(this, webView), "android");

        // charge / payment / history
//        webView.loadUrl("http://192.168.10.138:3000/charge");
        webView.loadUrl(StaticFinalLabelsClass.WEB_IP_ADDRESS+"/main/charge");
//        webView.loadUrl("http://192.168.10.138:8080/public/sample");



    }
}