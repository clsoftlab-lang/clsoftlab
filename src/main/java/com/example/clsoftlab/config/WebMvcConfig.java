package com.example.clsoftlab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.clsoftlab.config.interceptor.LoginCheckInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	@Autowired
	private LoginCheckInterceptor loginCheckInterceptor;
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")          // 1. 모든 경로에 대해 검사 적용
                .excludePathPatterns("/login", "/assets/**"); // 2. 로그인 페이지나 정적 리소스는 검사 제외
    }
}
