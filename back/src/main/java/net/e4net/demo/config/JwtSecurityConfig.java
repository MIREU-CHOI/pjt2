package net.e4net.demo.config;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import net.e4net.demo.jwt.JwtFilter;
import net.e4net.demo.jwt.TokenProvider;

// 직접 만든 TokenProvider와 JwtFilter를 SecurityConfig에 적용할 때 사용
/* 메인 메소드인 configure은TokenProvider를 주입받아서 JwtFilter를 통해 
 * SecurityConfig 안에 필터를 등록하게 되고, 
 * 스프링 시큐리티 전반적인 필터에 적용 */
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{

	private final TokenProvider tokenProvider;

    @Override
    public void configure(HttpSecurity http) {
        JwtFilter customFilter = new JwtFilter(tokenProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
	
}
