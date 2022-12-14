package com.example.e4net;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    // for 자동 로그인
    String id, pw;
    CheckBox cb_loginSave;
    private Context mContext;


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
//        getSupportActionBar().hide();

        mContext = this;    // 필수

        et_loginId = findViewById(R.id.et_loginId);
        et_loginPwd = findViewById(R.id.et_loginPwd);
        btn_login = findViewById(R.id.btn_login);

        tv_signUp = findViewById(R.id.tv_signUp);
        tv_findPwd = findViewById(R.id.tv_findPwd);

        cb_loginSave = findViewById(R.id.cb_loginSave);

        sharedPreferences = getSharedPreferences("e4_default", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // 로그인 정보 기억하기 체크 유무 확인
        boolean bool = PreferenceManager.getBoolean(mContext, "check");
        if (bool) { // 체크가 되어있다면 아래 코드를 수행
            // 저장된 아이디와 암호를 가져와 셋팅한다.
            et_loginId.setText(PreferenceManager.getString(mContext, "id"));
            et_loginPwd.setText(PreferenceManager.getString(mContext, "pw"));
            cb_loginSave.setChecked(true);  // 체크박스는 여전히 체크 표시 하도록 셋팅
        }

        // ***** 레트로핏 생성 *****
        retrofit = new Retrofit.Builder()
                .baseUrl(StaticFinalLabelsClass.SERVER_IP_ADDRESS)  // http://192.168.10.138:8888
//                .baseUrl("http://192.168.35.117:8888")
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
                Intent intent = new Intent(LoginActivity.this, WebviewFindpwdActivity.class);
//                intent.putExtra("msg", "from login");
//                Toast toast = Toast.makeText(getApplicationContext(), "비번 까먹었닝", Toast.LENGTH_SHORT);
//                toast.show();
                startActivity(intent);
            }
        });

        // ========== "로그인" 버튼 ==========
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MembDTO memb = new MembDTO();
                //아이디 암호 입력창에서 텍스트를 가져와 PreferenceManager에 저장함
                PreferenceManager.setString(mContext, "id", et_loginId.getText().toString()); // id라는 키값으로 저장
                PreferenceManager.setString(mContext, "pw", et_loginPwd.getText().toString()); // pw라는 키값으로 저장

                // 저장한 키 값으로 저장된 아이디와 암호를 불러와 String 값에 저장
                String checkId = PreferenceManager.getString(mContext, "id");
                String checkPw = PreferenceManager.getString(mContext, "pw");

                //아이디와 암호가 비어있는 경우를 체크
                if (TextUtils.isEmpty(checkId) || TextUtils.isEmpty(checkPw)){
                    // 아이디나 암호 둘 중 하나가 비어있으면 토스트메시지를 띄운다
                    Toast.makeText(LoginActivity.this, "아이디/암호를 입력해주세요",
                            Toast.LENGTH_SHORT).show();
                }else { //둘 다 충족하면 다음 동작 실행
                    String userId = et_loginId.getText().toString();
                    String userPwd = et_loginPwd.getText().toString();
                    memb.setMembId(userId);
                    memb.setMembPwd(userPwd);

                    Call<TokenDTO> request = retrofitService.login(memb);
                    request.enqueue(new Callback<TokenDTO>() {
                        @Override
                        public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                            if (response.isSuccessful()){
                                tokenDTO = response.body();
                                Log.d("[login]", "onResponse: 성공,\n getMembId => "+ tokenDTO.getMembId());
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        tokenDTO.getMembId()+"님, 환영합니다! ^o^", Toast.LENGTH_SHORT);
                                toast.show();

//                            editor.putString("membId", tokenDTO.getMembId());
//                            editor.putLong("membSn", tokenDTO.getMembSn());
//                            editor.putString("accessToken", tokenDTO.getAccessToken());
                                Gson gson = new Gson();
                                String data = gson.toJson(tokenDTO);
                                editor.putString("data", data);
                                editor.apply(); // commit()은 동기, apply()는 비동기 처리
//                            String id = sharedPreferences.getString("membId", "membId");
                                Log.d("[login]", "id => "+userId+"\n pwd => "+userPwd);
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
                    Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
                    intent.putExtra("msg", "from login");
                    startActivity(intent);
                }

                // 미르 ver.1 --------------------------------------------
//                MembDTO memb = new MembDTO();
//                String userId = et_loginId.getText().toString();
//                String userPwd = et_loginPwd.getText().toString();
//                memb.setMembId(userId);
//                memb.setMembPwd(userPwd);

//                if (cb_loginSave.isChecked()){
//                    editor.putString("userId", userId);
//                    editor.putString("userPwd", userPwd);
//                    editor.apply();
////                  commit()은 동기, apply()는 비동기 처리
//                }

//                Call<TokenDTO> request = retrofitService.login(memb);
//                request.enqueue(new Callback<TokenDTO>() {
//                    @Override
//                    public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
//                        if (response.isSuccessful()){
//                            tokenDTO = response.body();
//                            Log.d("[login]", "onResponse: 성공,\n getMembId => "+ tokenDTO.getMembId());
//                            Toast toast = Toast.makeText(getApplicationContext(),
//                                    tokenDTO.getMembId()+"님, 환영합니다! ^o^", Toast.LENGTH_SHORT);
//                            toast.show();
//
////                            editor.putString("membId", tokenDTO.getMembId());
////                            editor.putLong("membSn", tokenDTO.getMembSn());
////                            editor.putString("accessToken", tokenDTO.getAccessToken());
//                            Gson gson = new Gson();
//                            String data = gson.toJson(tokenDTO);
//                            editor.putString("data", data);
//                            editor.apply();
////                            editor.commit();
////                            String id = sharedPreferences.getString("membId", "membId");
//                            Log.d("[login]", "id => ");
//                            Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
//                            intent.putExtra("msg", "from login");
//                            startActivity(intent);
//                        } else {
//                            Log.d("[login]", "onResponse: 실패, \n"+response.toString());
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<TokenDTO> call, Throwable t) {
//                        Log.d("[login]", "onFailure => "+t.getMessage());
//                    }
//                });
            }
        });
        //로그인 기억하기 체크박스 유무에 따른 동작 구현
        cb_loginSave.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox)view).isChecked()) { // 체크박스 체크 되어 있으면
                    // editText에서 아이디와 암호 가져와 PreferenceManager에 저장한다.
                    PreferenceManager.setString(mContext, "id", et_loginId.getText().toString()); //id 키값으로 저장
                    PreferenceManager.setString(mContext, "pw", et_loginPwd.getText().toString()); //pw 키값으로 저장
                    PreferenceManager.setBoolean(mContext, "check", cb_loginSave.isChecked()); //현재 체크박스 상태 값 저장
                } else { // 체크박스가 해제되어있으면
                    PreferenceManager.setBoolean(mContext, "check", cb_loginSave.isChecked()); //현재 체크박스 상태 값 저장
                    PreferenceManager.clear(mContext); //로그인 정보를 모두 날림
                }
            }
        }) ;










    }
}