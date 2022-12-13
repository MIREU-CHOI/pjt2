package net.e4net.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.e4net.demo.dto.MemberDTO;
import net.e4net.demo.dto.TokenDTO;
import net.e4net.demo.service.AuthService;

@RestController
//@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<MemberDTO> signup(@RequestBody MemberDTO requestDto) {
		log.debug("AuthController Layer :: Call signup Method!");
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenDTO> login(@RequestBody MemberDTO requestDto, HttpServletRequest request) {
    	log.debug("AuthController :: login! \n	입력한 id: {} pwd: {} ", requestDto.getMembId(), requestDto.getMembPwd());
        return ResponseEntity.ok(authService.login(requestDto, request.getRemoteAddr()));
    }
    
    
    
    
}





