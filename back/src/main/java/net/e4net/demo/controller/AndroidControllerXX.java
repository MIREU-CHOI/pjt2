package net.e4net.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AndroidControllerXX {

	// 여기를 쓰지 말고 android 폴더 안에 있는 컨트롤러로 쓰자!
	
	
	// 아임포트 API - 머니충전, 결제 
	@RequestMapping("/android/iamport")
	public String callIamport() {
		log.debug("Android 아임포트!");
		return "iamport";
	}
	
	
}
