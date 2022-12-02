package com.example.e4net;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    TextView tv_signUp;
    TextView tv_findPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 회원가입 페이지로 넘어가는 버튼
        tv_signUp = findViewById(R.id.tv_signUp);
        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                intent.putExtra("msg", "from login");
                startActivity(intent);
            }
        });

        // 비밀번호 찾기 버튼
        tv_findPwd = findViewById(R.id.tv_findPwd);
        tv_findPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
//                intent.putExtra("msg", "from login");
                Toast toast = Toast.makeText(getApplicationContext(), "비번 까먹었닝", Toast.LENGTH_SHORT);
                toast.show();
                startActivity(intent);
            }
        });
    }
}