package com.example.app1125;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tv_result;
    Button btn_get;

    Retrofit retrofit;
    RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.10.138:8080")              // baseUrl 등록
                .addConverterFactory(GsonConverterFactory.create()) // Json을 변환해줄 Gson
                .build();
        retrofitService = retrofit.create(RetrofitService.class);

        tv_result = findViewById(R.id.tv_result);
        btn_get = findViewById(R.id.btn_get);

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Map<String, Object>> request = retrofitService.getSampleData("data1");
                request.enqueue(new Callback<Map<String, Object>>() {
                    @Override   // 응답 받았을 때
                    public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                        if (response.isSuccessful()){
                            Map<String, Object> body = response.body();
                            tv_result.setText("body="+body);
                        }else {
                            tv_result.setText("error="+response.errorBody());
                        }
                    }

                    @Override   // 응답 받기 실패할 때
                    public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                        tv_result.setText("fail="+t.getLocalizedMessage());
                    }
                });  // enqueue()  :  비동기 방식  / execute()  :  동기 방식
            }
        });
    }
}