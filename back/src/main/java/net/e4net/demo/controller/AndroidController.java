package net.e4net.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AndroidController {

	@RequestMapping("/android/post")
	public String getPost() {
		return "daum";	
	}
}
