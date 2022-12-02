package net.e4net.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HomeController {
	
	
	
	// ("") 이렇게만 주면 root url을 통하는 것 
	@RequestMapping("/")
	public String test() {
		return "test!!!";
	}
	
	
	
}
