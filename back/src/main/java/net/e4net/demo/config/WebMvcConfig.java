package net.e4net.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * preflight
 * Simple request가 아닌 요청 메시지보다 먼저 보내는 메시지로, 
 * 브라우저는 응답값으로 실제 데이터 전송 여부를 판단.
 *
 * CORS는 응답이 Access-Control-Allow-Credentials: true 을 가질 경우, 
 * 		Access-Controll-Allow-Origin의 값으로 *를 사용하지 못하게 막고 있다
 * Access-Control-Allow-Credentials: true를 사용하는 경우는 
 * 		사용자 인증이 필요한 리소스 접근이 필요한 경우인데,
 * 		만약 Access-Control-Allow-Origin: *를 허용한다면,
 * 		CSRF 공격에 매우 취약해져 악의적인 사용자가 인증이 필요한 리소스를 마음대로 접근할 수 있음.
*/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	   @Override
	   public void addCorsMappings(CorsRegistry registry) {
	      registry.addMapping("/**") 		// 프로그램에서 제공하는 URL 
	            .allowedOrigins("http://localhost:3000/", "https://online-pay.kakao.com") // 허용할 URL
	            .allowCredentials(true) 	// 쿠키 요청을 허용한다(다른 도메인 서버에 인증하는 경우에만 사용해야하며, true 설정시 보안상 이슈가 발생할 수 있다) 
	            .allowedMethods("OPTIONS","GET","POST",
	                  "PUT","DELETE");		// 어떤 메서드를 허용할 것인지
	      	// .maxAge(1500); 	// preflight 요청에 대한 응답을 브라우저에서 캐싱하는 시간
	   }
}


