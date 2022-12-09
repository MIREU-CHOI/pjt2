package com.example.demo.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import jakarta.annotation.PostConstruct;

@Service
public class FcmService {

	public static final String FB_CONFIG_PATH = "android1125-firebase-adminsdk-bekom-13ace6583b.json";
	
	@PostConstruct
	public void initFbApp() {
		if (! FirebaseApp.getApps().isEmpty()) {
			return;
		}
		try {
			GoogleCredentials credentials = GoogleCredentials
					.fromStream(new ClassPathResource(FB_CONFIG_PATH).getInputStream());
			FirebaseOptions options = FirebaseOptions.builder()
										.setCredentials(credentials)
										.build();
			FirebaseApp.initializeApp(options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String sendPush(Map<String, Object> param) {
		Message message = Message.builder()
							.putData("title", (String)param.get("title"))
							.putData("content", (String)param.get("content"))
							.setToken((String)param.get("token"))
							.build();
		try {
			return FirebaseMessaging.getInstance().send(message);
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
		}
		return "fail";
	}
	
}
