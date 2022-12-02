package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class PrefActivity extends AppCompatActivity {

    Switch sw_toggle;
    Button btn_update;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);

        textView2 = findViewById(R.id.textView2);

        btn_update = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrefActivity.this, SampleActivity.class);
                intent.putExtra("msg", "from pref");
                startActivity(intent);
            }
        });

        sharedPreferences = getSharedPreferences("pref_default", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        sw_toggle = findViewById(R.id.sw_toggle);
        sw_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("checked", b);
                editor.apply(); // 값 저장
            }
        });

        boolean checked = sharedPreferences.getBoolean("checked", false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String text = sharedPreferences.getString("EditText", "");
        textView2.setText(text);
    }
}