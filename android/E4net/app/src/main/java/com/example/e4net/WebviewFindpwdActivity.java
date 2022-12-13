package com.example.e4net;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebviewFindpwdActivity extends AppCompatActivity {

    private WebView wv_findpwd;
    private ProgressBar pb_findpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_findpwd);

        wv_findpwd = findViewById(R.id.wv_findpwd);
        pb_findpwd = findViewById(R.id.pb_findpwd);

        wv_findpwd.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                String id = sharedPreferences.getString("membId", "membId");
                Log.d("[webview]", "Findpwd shouldOverrideUrlLoading, request => "+request.toString());
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                String id = sharedPreferences.getString("membId", "membId");
                Log.d("[webview]", "Findpwd onPageStarted, url => "+url);
//                tv_membId.setText(id);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("[webview]", "Findpwd onPageFinished, url="+url);
                super.onPageFinished(view, url);
            }
        });


        wv_findpwd.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("app title")
                        .setMessage("app" + message)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("[webView]", "Findpwd 확인버튼 클릭");
                                result.confirm();
                            }
                        })
                        .setCancelable(false)
                        .create().show();
                //return super.onJsAlert(view, url, message, result);
                return true;
            }
        });

        WebSettings webSettings = wv_findpwd.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);

        wv_findpwd.addJavascriptInterface(new JsInterface(this, wv_findpwd), "android");
        wv_findpwd.loadUrl(StaticFinalLabelsClass.SERVER_IP_ADDRESS+"/android/findPwd2");


    }
}