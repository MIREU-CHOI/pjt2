package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SampleActivity extends AppCompatActivity {

    EditText et_edit;
    Button btn_save;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        sharedPreferences = getSharedPreferences("pref_default", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        et_edit = findViewById(R.id.et_edit);
        btn_save = findViewById(R.id.btn_save);

        et_edit.addTextChangedListener(new TextWatcher() {
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

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = String.valueOf(et_edit.getText());
//                String text = et_edit.getText() != null ? et_edit.getText().toString() : "";
//                if (text.length() > 0){
//                    Intent intent = new Intent(SampleActivity.this, PrefActivity.class);
//                    intent.putExtra("text", text);
//                    startActivity(intent);
//
//                }
                editor.putString("EditText",text);
                editor.apply();
                finish();
//                intent.putExtra("msg", "from sample activity");
            }
        });

    }
}