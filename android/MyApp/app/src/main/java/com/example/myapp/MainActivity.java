package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private long backPressedTime = 0;
    private final long intervalTime = 2000;

    Button btn_next;
    EditText et_url;
    Button btn_open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("[mainactivity]", "onCreate");

        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NextActivity.class);
                intent.putExtra("msg", "from main");
                startActivity(intent);
            }
        });

        et_url = findViewById(R.id.et_url);
        btn_open = findViewById(R.id.btn_open);

        et_url.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("[edittext]", "문자열길이="+charSequence.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = et_url.getText() != null ? et_url.getText().toString() : "";
                if (text.length() > 0){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(text));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null){
            String msg = intent.getStringExtra("msg");
            if (msg != null) Toast.makeText(this, "onNewIntent="+msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("[mainactivity]", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("[mainactivity]", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("[mainactivity]", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("[mainactivity]", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("[mainactivity]", "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("[mainactivity]", "onDestroy");
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - backPressedTime > intervalTime){
            Toast.makeText(this, "한 번 더 누르면 종료", Toast.LENGTH_SHORT).show();
            backPressedTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }

    }
}