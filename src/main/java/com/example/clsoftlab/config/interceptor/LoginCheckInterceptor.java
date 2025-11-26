package com.example.clsoftlab.config.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        String requestURI = request.getRequestURI();
        
        // 1. 세션 가져오기 (없으면 null 반환)
        HttpSession session = request.getSession(false);

        // 2. 로그인 여부 체크
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            System.out.println("🚨 미인증 사용자 요청 : " + requestURI);
            
            // 로그인으로 리다이렉트 (화면 이동)
            response.sendRedirect("/login");
            
            // 더 이상 컨트롤러를 실행하지 않음 (중단)
            return false;
        }

        // 3. 로그인 된 사용자면 통과
        return true;
    }
}
