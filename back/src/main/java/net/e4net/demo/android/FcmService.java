package net.e4net.demo.android;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class FcmService {


    public static final String FB_CONFIG_PATH ="e4net-8a1bc-firebase-adminsdk-q66lk-7153519ee5.json";

    @PostConstruct
    public void initFbApp(){
        if(! FirebaseApp.getApps().isEmpty()){
            return;
        }
        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(new ClassPathResource(FB_CONFIG_PATH).getInputStream());
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // push알림 보내는 service 함수 <= 이 잡채가 안드로이드에 푸시알림을 보내는 행위!!!
    public String sendPush(Map<String,Object> param){
    	log.debug("\n	FcmService :: param => {}", param.toString());
    	String title = "";
    	String cont = "";
    	String money = param.get("money").toString();
    	if ((param.get("cate").toString()).equals("pay")){
    		title = "결제 메세지";
    		cont = money+"원 결제 완료되었습니다!";
    		log.debug("\n	FcmService :: cont => {}",cont);
  	  	} else if ((param.get("cate").toString()).equals("charge")){
    		title = "충전 메세지";
    		cont = money+"원 충전 완료되었습니다!";
  	  	}
        Message message = Message.builder()
                .putData("title", title)
                .putData("content", cont)
                .setToken((String)param.get("token")) // 리액트 axois로 보낸 토큰을 set해주면 됨! 
                .build();
        try {
            return  FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        return "fail";
    }
 // push알림 보내는 service 함수 
    public String pushCerNum(Map<String,Object> param){
    	log.debug("\n	FcmService :: param => {}", param.toString());
    	String title = "E4. 휴대폰 인증번호";
    	String cont = "E4. Pay 휴대폰 인증번호는 " + "[ 7963 ]" + "입니다.";
//    	String money = param.get("money").toString();
//    	if ((param.get("cate").toString()).equals("pay")){
//    		title = "결제 메세지";
//    		cont = money+"원 결제 완료되었습니다!";
//    		log.debug("\n	FcmService :: cont => {}",cont);
//  	  	} else if ((param.get("cate").toString()).equals("charge")){
//    		title = "충전 메세지";
//    		cont = money+"원 충전 완료되었습니다!";
//  	  	}
        Message message = Message.builder()
                .putData("title", title)
                .putData("content", cont)
                .setToken((String)param.get("token")) // 리액트 axois로 보낸 토큰을 set해주면 됨! 
                .build();
        try {
        	log.debug("\n	푸시 알람 성공!!!");
            return  FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
        	log.debug("\n	푸시 알람 실패???");
            e.printStackTrace();
        }
        return "fail";
    }

}