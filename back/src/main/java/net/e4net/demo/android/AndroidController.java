package net.e4net.demo.android;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AndroidController {
	
	@Autowired private FcmService fcmService;

	// ver.1
//	@RequestMapping("/android/post")
//	public String getPost() {
//		return "daum_address.html";	
//	}
	
	// 다음 우편번호 API
	@RequestMapping("/android/post")
	public String getPost() {
		log.debug("Android 다음 우편번호!");
		return "daum";	
	}
	
	
	/* fcm push */
	@RequestMapping("/android/fcm")
	public String goFcmPage() {
		return "fcmPage";
	}
	
	@GetMapping("/android/fcm/push")
	public @ResponseBody Map<String, Object> sendFcmPush(@RequestParam Map<String, Object> param){
		Map<String, Object> map = new HashMap<>();
		map.put("param", param);
		map.put("result", fcmService.sendPush(param));
		return map;
	}
	
	
}
