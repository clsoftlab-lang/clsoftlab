package com.example.clsoftlab.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.clsoftlab.config.interceptor.AuthInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer{

	private final AuthInterceptor authInterceptor; 
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**") // 1. 모든 경로 검사
                .excludePathPatterns(   // 2. 검사 제외 경로 (White List)
                    "/login",           // 로그인 화면
                    "/logout",          // 로그아웃 API
                    "/assets/**",       // 정적 리소스 (이미지, CSS, JS 등)
                    "/error",           // 스프링 부트 기본 에러 페이지
                    "/favicon.ico"      // 파비콘
                ); 
    }
}
