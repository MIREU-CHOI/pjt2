package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AndroidController {

	@RequestMapping("/android/post")
	public String getPost() {
		return "daum_address.html";	
	}
}
