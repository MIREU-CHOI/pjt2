package com.example.e4net;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class JavascriptCallbackClient {

    private Activity activity;
    private WebView webView;

    public JavascriptCallbackClient(Activity activity, WebView webView) {
        this.activity = activity;
        this.webView = webView;
    }

    private String publishEvent(String functionName, String data) {
        StringBuffer buffer = new StringBuffer()
                .append("window.dispatchEvent(\n")
                .append("   new CustomEvent(\"").append(functionName).append("\", {\n")
                .append("           detail: {\n")
                .append("               data: ").append(data).append("\n")
                .append("           }\n")
                .append("       }\n")
                .append("   )\n")
                .append(");");
        return buffer.toString();
    }

    @JavascriptInterface
    public void showToastMessage(final String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void callJavaScriptFunction() {
        webView.postDelayed(() -> {
            webView.evaluateJavascript(publishEvent("javascriptFunction", "\"Hello, I'm message from Android\""),
                    (result) -> {
                        Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
                    }
            );
        }, 5000);
    }






}
