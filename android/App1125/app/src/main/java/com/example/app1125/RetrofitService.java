package com.example.app1125;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {

    // 사용할 메소드 정의
    @GET("/public/sample/data")
    Call<Map<String, Object>> getSampleData(@Query("param") String param);
}
