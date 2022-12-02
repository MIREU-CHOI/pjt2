package com.example.e4net;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {
    Button btn_goLogin; // (임시 버튼)

    boolean allChkVal = false;  // idChkVal 과 phoneChkVal, 모두 true 일 때만 '가입 성공'
    boolean idChkVal = false;   // 아이디 중복확인
    boolean phoneChkVal = false;// 휴대폰번호 확인

    EditText et_id;
    Button btn_idChk;
    EditText et_phone;
    Button btn_phoneCertSend;
    String certNum;             // sts에서 넘어온 휴대폰 인증번호
    String userCertNum;         // 유저가 입력한 인증번호
    EditText et_userCertNum;
    Button btn_phoneCertChk;
    Button btn_signUp;

    // 레트로핏
    Retrofit retrofit;
    RetrofitService retrofitService;

    // 우편번호
    private static final int SEARCH_ADDRESS_ACTIVITY = 1002;
    private EditText et_postNum;    // 우편번호 입력란
    EditText et_postAddr;
    ActivityResultLauncher<Intent> launcher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.10.138:8888")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);

        // ========== 아이디 중복확인 ==========
        et_id = findViewById(R.id.et_id);
        btn_idChk = findViewById(R.id.btn_idChk);
        btn_idChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idVal = et_id.getText().toString();
                Log.d("[idChk]", "onClick: idVal => "+idVal);
                Call<Map<String, Boolean>> request = retrofitService.checkMembIdDuplicate(idVal);
                request.enqueue(new Callback<Map<String, Boolean>>() {
                    @Override
                    public void onResponse(Call<Map<String, Boolean>> call, Response<Map<String, Boolean>> response) {
                        if (response.isSuccessful()){
                            Map<String, Boolean> body = response.body();
                            Log.d("[idChk]", "onResponse: 성공,\n결과"+ body.toString());
                            idChkVal =  body.get("res");
                            if (idChkVal){
                                Toast toast = Toast.makeText(getApplicationContext(), "사용하실 수 있는 아이디입니다.", Toast.LENGTH_SHORT);
                                toast.show();
                            }else{
                                Toast toast = Toast.makeText(getApplicationContext(), "이미 사용 중인 아이디입니다.", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        } else {
                            Log.d("[idChk]", "onResponse: 실패, \n"+response.toString());
                            Log.d("[idChk]", "onResponse: 실패, \n"+response.errorBody());
                        }
                    }
                    @Override
                    public void onFailure(Call<Map<String, Boolean>> call, Throwable t) {
                        Log.d("[idChk]", "onFailure => "+t.getMessage());
                    }
                });
            }
        });

        // ========== 휴대폰번호 인증번호 "전송" ==========
        et_phone = findViewById(R.id.et_phone);
        btn_phoneCertSend = findViewById(R.id.btn_phoneCertSend);
        btn_phoneCertSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneVal = et_phone.getText().toString();
                Call<String> request = retrofitService.sendSMS(phoneVal);
                request.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()){
                            certNum = response.body();
                            Log.d("[phoneChk]", "onResponse: 성공,\n결과"+ certNum);
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "인증번호가 전송되었습니다.", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            Log.d("[phoneChk]", "onResponse: 실패, \n"+response.toString());
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("[phoneChk]", "onFailure => "+t.getMessage());
                    }
                });
            }
        });
        // ========== 휴대폰번호 인증번호 "확인" ==========
        et_userCertNum = findViewById(R.id.et_userCertNum);
        btn_phoneCertChk = findViewById(R.id.btn_phoneCertChk);
        btn_phoneCertChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCertNum = et_userCertNum.getText().toString();
                Log.d("[phoneChk]", "onResponse: 성공,\nuserCertNum => "+ userCertNum
                                        + "\n certNum => "+certNum);
                if (certNum.equals(userCertNum)){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "인증 완료되었습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    phoneChkVal = true;
//                    Log.d("[phoneChk]", "phoneChkVal: "+phoneChkVal);
                } else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "인증 실패하였습니다. 다시 인증하세요!", Toast.LENGTH_SHORT);
                    toast.show();
//                    Log.d("[phoneChk]", "phoneChkVal: "+phoneChkVal);
                }
            }
        });

        // ========== 우편번호 ==========
        et_postNum = (EditText) findViewById(R.id.et_postNum);
        et_postAddr = findViewById(R.id.et_postAddr);
        et_postNum.setFocusable(false); // 터치 막기
        et_postAddr.setFocusable(false);
        Button btn_postSear = (Button) findViewById(R.id.btn_postSear);
        btn_postSear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("[postSear]", "주소 검색 클릭");
                int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
                if(status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {
                    Intent i = new Intent(getApplicationContext(), WebviewActivity.class);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }else {
                    Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ========== 회원가입 ==========
        btn_signUp = findViewById(R.id.btn_signUp);
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });









        // (임시 버튼) 로그인 페이지로 넘어가는 버튼
        btn_goLogin = findViewById(R.id.btn_goLogin);
        btn_goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                intent.putExtra("msg2", "from signup");
                startActivity(intent);
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
//                        et_postNum.setText(data); // ver.1
                        // data의 정보를 각각 우편번호와 실주소로 나누어 EditText에 표시
                        et_postNum.setText(data.substring(0,5));
                        et_postAddr.setText(data.substring(7));
                    }
                }
                break;
        }
    }
}