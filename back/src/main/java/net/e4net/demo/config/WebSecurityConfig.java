package net.e4net.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import net.e4net.demo.jwt.JwtAccessDeniedHandler;
import net.e4net.demo.jwt.JwtAuthenticationEntryPoint;
import net.e4net.demo.jwt.TokenProvider;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
public class WebSecurityConfig {
	private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    
    // request로부터 받은 비밀번호를 암호화하기 위해 PasswordEncoder 빈을 생성
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // filterChain - 실질적인 로직
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() 	// https만을 사용하기위해 httpBasic을 disable
                // 리액트에서 token을 localstorage에 저장할 것이기 때문에 
                .csrf().disable()  // csrf 방지 또한 disable
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
                // REST API를 통해 세션 없이 토큰을 주고받으며 데이터를 주고받기 때문에 세션설정 또한 STATELESS

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .authorizeRequests()
                // 로그인한 유저만 id중복체크 기능을 할 수 있도록 테스트
//                .antMatchers("/member/exists/**").hasRole("USER")
//                .antMatchers("/goods/new").hasRole("USER")
                .antMatchers("/**","/").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/member/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/auth/login").permitAll()
//                .antMatchers("/goods/finall").hasRole("USER")
//                .antMatchers("/goods/**").hasRole("seller")
                .anyRequest().authenticated()
                // 1. 로그인할 때 세션스토리지에 토큰 저장
                // 2. 권한 필요한 기능들 프론트에서 axios 호출할 때 세션에 저장한 토큰 헤더에 심기 
                // 3. WebSecurityConfig.java(여기)에 권한 설정  .antMatchers("/member/exists/**").hasRole("USER")

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }
	
}
