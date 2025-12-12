package com.example.clsoftlab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.clsoftlab.dto.common.LoginRequestDto;
import com.example.clsoftlab.dto.common.LoginResultDto;
import com.example.clsoftlab.service.common.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	
	// 로그인
	@GetMapping("/login")
	public String loginPage () {
		return "login";
	}
	
	// 로그인 시도
	@PostMapping("/login")
	public ResponseEntity<?> login (@RequestBody @Valid LoginRequestDto dto, HttpServletRequest request) {
		// 접속 정보 추출
        String clientIp = getClientIp(request);
        String userAgent = request.getHeader("User-Agent");

        try {
            // 서비스 호출
        	LoginResultDto loginResult = loginService.login(dto, clientIp, userAgent);

            // 세션 처리 (로그인 성공 시)
            HttpSession session = request.getSession();
            session.invalidate(); // 기존 세션 파기 (보안)
            session = request.getSession(true); // 새 세션 생성
            
            session.setAttribute("LOGIN_USER", loginResult.getUserDto());
            session.setAttribute("USER_PERMS", loginResult.getPermMap());
            session.setAttribute("USER_MENU_TREE", loginResult.getMenuTree());
            session.setMaxInactiveInterval(10800); // 180분 유지

            
            System.out.println(loginResult.getMenuTree());
            // 성공 응답 (200 OK + 사용자 정보)
            return ResponseEntity.ok(loginResult.getUserDto()); 

        } catch (Exception e) {
            // 실패 응답 (400 Bad Request + 에러 메시지)
            // JS의 error 콜백에서 이 메시지(e.getMessage())를 alert로 띄우게 됩니다.
            return ResponseEntity.badRequest().body(e.getMessage());
        }
	}
	
	// 3. 로그아웃 처리 (API)
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); 
        }
        return ResponseEntity.ok("logged_out");
    }
	
	// [유틸] IP 주소 추출 (프록시 환경 고려)
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
