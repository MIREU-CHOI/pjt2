package net.e4net.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AndroidController {

	// 다음 우편번호 API
	@RequestMapping("/android/post")
	public String getPost() {
		log.debug("Android 다음 우편번호!");
		return "daum";	
	}
	
	// 아임포트 API - 머니충전, 결제 
	@RequestMapping("/android/iamport")
	public String callIamport() {
		log.debug("Android 아임포트!");
		return "iamport";
	}
	
	
}
