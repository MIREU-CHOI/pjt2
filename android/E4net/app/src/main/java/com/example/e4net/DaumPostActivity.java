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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class DaumPostActivity extends AppCompatActivity {

    private WebView wv_search_address;
    private ProgressBar progress;
//    private EditText progress;
//    private EditText progressBar;

    class MyJavaScriptInterface {
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
        setContentView(R.layout.activity_daum_post);

        progress = findViewById(R.id.web_progress);
        wv_search_address = findViewById(R.id.wv_search_address);

        wv_search_address.getSettings().setJavaScriptEnabled(true);
        wv_search_address.getSettings().setDomStorageEnabled(true);       // for 2

        wv_search_address.addJavascriptInterface(new MyJavaScriptInterface(), "Android");

        // ver.1
//        wv_search_address.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                wv_search_address.loadUrl("javascript:sample2_execDaumPostcode();");
//            }
//        });
//        wv_search_address.loadUrl("http://192.168.10.138:8888/daum.html");

        // ver.2
        wv_search_address.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.d("[Webview]", "onReceivedSslError: ??????, \n error: "+error);
                handler.proceed(); // SSL ????????? ???????????? ?????? ??????
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("[Webview]", "shouldOverrideUrlLoading: \n url => "+url);
                view.loadUrl(url);
                return true;
            }
            // ????????? ?????? ????????? ??????
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d("[Webview]", "onPageStarted: \n url => "+url);
                progress.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("[Webview]", "onPageFinished: \n url => "+url);
                progress.setVisibility(View.GONE);
                wv_search_address.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });

        //ssl ????????? ?????? ?????? ????????? ?????? ??????
        wv_search_address.setWebChromeClient(new WebChromeClient() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.d("[Webview]", "onPermissionRequest: \n request => "+request.getResources().toString());
                request.grant(request.getResources());
            }
        });
        wv_search_address.loadUrl(
                StaticFinalLabelsClass.SERVER_IP_ADDRESS+"/android/post");
//        wv_search_address.loadUrl("http://192.168.10.138:8888"+"/android/post");
//        wv_search_address.loadUrl("file:///android_asset/www/daum_address.html");
//        boolean showVal = wv_search_address.isShown();
//        boolean actiVal = wv_search_address.isActivated();
//        Log.d("[Webview]", "?????? url isShown? : "+showVal);
//        Log.d("[Webview]", "?????? url isActivated? : "+actiVal);
    }
}