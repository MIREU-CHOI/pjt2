package com.example.app1125;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class JsInterface {

    Context context;
    Activity activity;
    WebView webView;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public JsInterface(Context context, WebView webView, SharedPreferences sharedPreferences, SharedPreferences.Editor editor){
        this.context = context;
        this.activity = (Activity) context;
        this.webView = webView;
        this.editor = editor;
        this.sharedPreferences = sharedPreferences;
//        this.NOTI_CHANNEL_ID = NOTI_CHANNEL_ID;

    }

    @JavascriptInterface
    public void appFunction(String msg) {
        Toast.makeText(context, "in app="+msg, Toast.LENGTH_SHORT).show();
//        activity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                webView.loadUrl("javascript:jsFunction('app msg')");
//            }
//        });
    }
    // ---------- 실습 ----------
    // 재호
    NotificationManager manager;
    NotificationCompat.Builder builder;

    @JavascriptInterface
    public void savetext(String a) {
        editor.putString("text" ,a );
        editor.apply();
    }
    @JavascriptInterface
    public void saveon() {
        editor.putBoolean("pushalarm" , true);
        editor.apply();
    }
    @JavascriptInterface
    public void saveoff(String msg) {
        boolean a =  sharedPreferences.getBoolean("pushalarm",false);
        editor.putBoolean("pushalarm" , false);
        editor.apply();
    }
    @JavascriptInterface
    public void startfunc(String msg) {
        boolean a =  sharedPreferences.getBoolean("pushalarm",true);
        String b =  sharedPreferences.getString("text","hi");
        if (a == true){
            Toast.makeText(context, b , Toast.LENGTH_SHORT).show();
        }
    }
    @JavascriptInterface
    public void gettext() {
        String b =  sharedPreferences.getString("text","hi");
        Toast.makeText(context, b , Toast.LENGTH_SHORT).show();
    }


    // 미르 
    @JavascriptInterface
    public void appPushOnSendFunc(String msg){
        Toast.makeText(context, "미르 테스트="+msg, Toast.LENGTH_SHORT).show();
//        activity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                webView.loadUrl("javascript:pushOnSendFunc('"+val+"')");
//            }
//        });
    }
    @JavascriptInterface
    public void appPushOnFunc(boolean isOn){
        Toast.makeText(context, "미르 테스트="+isOn, Toast.LENGTH_SHORT).show();
        editor.putBoolean("msg", isOn);
        editor.apply();
    }
    @JavascriptInterface
    public void appPushOffFunc(String msg){
        Toast.makeText(context, "미르 테스트="+msg, Toast.LENGTH_SHORT).show();
        editor.putString("msg", msg);
        editor.apply();
    }
}
