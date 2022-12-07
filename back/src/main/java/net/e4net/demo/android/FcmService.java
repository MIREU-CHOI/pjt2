package net.e4net.demo.android;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

@Service
public class FcmService {


//    public static final String FB_CONFIG_PATH ="android221125-b3103-firebase-adminsdk-kk4k8-9690a0b815.json";

//    @PostConstruct
//    public void initFbApp(){
//        if(! com.google.firebase.FirebaseApp.getApps().isEmpty()){
//            return;
//        }
//        try {
//            GoogleCredentials credentials = GoogleCredentials.fromStream(new ClassPathResource(FB_CONFIG_PATH).getInputStream());
//            FirebaseOptions options = FirebaseOptions.builder()
//                    .setCredentials(credentials)
//                    .build();
//            FirebaseApp.initializeApp(options);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    //push알림 보내는 service 함수
//    public String sendPush(Map<String,Object> param){
//        Message message = Message.builder()
//                .putData("title", (String) param.get("title"))
//                .putData("content", (String) param.get("content"))
//                .setToken((String)param.get("token"))
//                .build();
//        try {
//            return  FirebaseMessaging.getInstance().send(message);
//        } catch (FirebaseMessagingException e) {
//            e.printStackTrace();
//        }
//        return "fail";
//    }

}