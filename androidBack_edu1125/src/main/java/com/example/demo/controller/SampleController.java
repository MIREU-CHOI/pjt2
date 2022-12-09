package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.FcmService;

@Controller
public class SampleController {
	
	@Autowired private FcmService fcmService;
	// 방심하지마 귀여운 꼬마 아가씨,,, -Arsen Lüpi
	@RequestMapping("/public/sample")
	public String goSamplePage() {
//		return "samplePage";
		return "alarm";	// alarm.html 로 가랏
	}

	@GetMapping("/public/sample/data")
	public @ResponseBody Map<String, Object> sampleData(String param){
		Map<String, Object> data = new HashMap<>();
		data.put("result", true);
		data.put("data", "param=");
		return data;
	}
	
	@RequestMapping("/public/fcm")
	public String goFcmPage() {
		return "fcmPage";
	}
	
	@GetMapping("/public/fcm/push")
	public @ResponseBody Map<String, Object> sendFcmPush(@RequestParam Map<String, Object> param){
		Map<String, Object> map = new HashMap<>();
		map.put("param", param);
		map.put("result", fcmService.sendPush(param));
		return map;
	}
	
	
	
	
	
	
}
