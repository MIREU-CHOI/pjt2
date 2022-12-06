package com.example.e4net;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    // 로그인 ID / PWD
    EditText et_loginId;
    EditText et_loginPwd;
    Button btn_login;

    TextView tv_signUp;
    TextView tv_findPwd;

    // 레트로핏
    Retrofit retrofit;
    RetrofitService retrofitService;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TokenDTO tokenDTO = new TokenDTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_loginId = findViewById(R.id.et_loginId);
        et_loginPwd = findViewById(R.id.et_loginPwd);
        btn_login = findViewById(R.id.btn_login);

        tv_signUp = findViewById(R.id.tv_signUp);
        tv_findPwd = findViewById(R.id.tv_findPwd);

        // ***** 레트로핏 생성 *****
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.10.138:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);

        // ========== 회원가입 페이지로 넘어가는 버튼 ==========
        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                intent.putExtra("msg", "from login");
                startActivity(intent);
            }
        });

        // ========== 비밀번호 찾기 버튼 ==========
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

        // ========== "로그인" 버튼 ==========
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MembDTO memb = new MembDTO();
                memb.setMembId(et_loginId.getText().toString());
                memb.setMembPwd(et_loginPwd.getText().toString());


                sharedPreferences = getSharedPreferences("e4_default", MODE_PRIVATE);
                editor = sharedPreferences.edit();

                Call<TokenDTO> request = retrofitService.login(memb);
                request.enqueue(new Callback<TokenDTO>() {
                    @Override
                    public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                        if (response.isSuccessful()){
                            tokenDTO = response.body();
                            Log.d("[login]", "onResponse: 성공,\ngetMembId => "+ tokenDTO.getMembId());
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    tokenDTO.getMembId()+"님, 환영합니다! ^o^", Toast.LENGTH_SHORT);
                            toast.show();

//                            editor.putString("membId", tokenDTO.getMembId());
//                            editor.putLong("membSn", tokenDTO.getMembSn());
//                            editor.putString("accessToken", tokenDTO.getAccessToken());
                            Gson gson = new Gson();
                            String data = gson.toJson(tokenDTO);
                            editor.putString("data", data);
                            editor.apply();
//                            editor.commit();
//                            String id = sharedPreferences.getString("membId", "membId");
                            Log.d("[login]", "id => ");
                            Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
                            intent.putExtra("msg", "from login");
                            startActivity(intent);
                        } else {
                            Log.d("[login]", "onResponse: 실패, \n"+response.toString());
                        }
                    }
                    @Override
                    public void onFailure(Call<TokenDTO> call, Throwable t) {
                        Log.d("[login]", "onFailure => "+t.getMessage());
                    }
                });
            }
        });










    }
}