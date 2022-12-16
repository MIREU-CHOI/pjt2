package com.example.e4net;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    // *** 사용할 메소드 정의 ***
    // 아이디 중복확인
    @GET("/android/member/exists/{membId}")
    Call<Map<String, Boolean>> checkMembIdDuplicate(@Path("membId") String membId);

    // 휴대폰 - 인증번호 전송 버튼
    @GET("/check/sendSMS/{mobileNo}")
    Call<String> sendSMS(@Path("mobileNo") String mobileNo);
    // 휴대폰 - 인증번호 푸시알람 보내기
    @POST("/android/fcm/pushCerNum")
    Call<Map<String, Object>> pushCerNum(@Body Map<String,Object> param);

    // 회원 "가입하기" 버튼
    @POST("/auth/signup")
    Call<MembDTO> signup(@Body MembDTO membDTO);    // 바디 요청 시 사용

    // "로그인" 버튼
    @POST("/auth/login")
    Call<TokenDTO> login(@Body MembDTO membDTO);

    // "비밀번호 찾기" 버튼
//    Call<>





// ========== 안드로이드 교육 ==========
    @GET("/public/sample/data")
    Call<Map<String, Object>> getSampleData(@Query("param") String param);
}
