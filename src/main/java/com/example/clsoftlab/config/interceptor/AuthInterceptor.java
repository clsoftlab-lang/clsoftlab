package com.example.clsoftlab.config.interceptor;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.clsoftlab.dto.common.RolePermDetailDto;
import com.example.clsoftlab.dto.common.UserAccountResponseDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {

	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
		String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false);

        // 비로그인 사용자 체크 -> 로그인 페이지로 이동
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            System.out.println("🚨 미인증 사용자 요청: " + requestURI);
            response.sendRedirect("/login");
            return false;
        }
        
        if ("/".equals(requestURI)) {
        	return true;
        }

        UserAccountResponseDto user = (UserAccountResponseDto) session.getAttribute("LOGIN_USER");
        
        @SuppressWarnings("unchecked")
        Map<String, RolePermDetailDto> permMap = (Map<String, RolePermDetailDto>) session.getAttribute("USER_PERMS");

        // 슈퍼 관리자(ADMIN)는 모든 권한 프리패스 (검사 중단 및 통과)
        if ("ADMIN".equals(user.getRoleId())) {
            return true;
        }

        if (permMap == null) {
            System.out.println("⚠️ 권한 정보가 없는 사용자: " + user.getUserId());
            response.sendRedirect("/login"); // 다시 로그인 유도
            return false;
        }

        RolePermDetailDto perm = permMap.get(requestURI);

        if (perm == null) {
            System.out.println("⛔ 접근 권한 없음 (" + user.getUserId() + ") -> " + requestURI);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "이 페이지에 접근할 권한이 없습니다.");
            return false;
        }

        // 7. 상세 권한(Read/Write) 체크
        String method = request.getMethod(); // GET, POST, PUT, DELETE ...

        if ("GET".equalsIgnoreCase(method)) {
            // 조회(GET) 요청인데 읽기 권한이 'N'이면 차단
            if ("N".equals(perm.getRead())) {
                System.out.println("⛔ 조회 권한 없음 (" + user.getUserId() + ") -> " + requestURI);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "조회 권한이 없습니다.");
                return false;
            }
        } else {
            // 데이터 변경(POST 등) 요청인데 쓰기 권한이 'N'이면 차단
            if ("N".equals(perm.getWrite())) {
                System.out.println("⛔ 쓰기 권한 없음 (" + user.getUserId() + ") -> " + requestURI);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "저장/수정/삭제 권한이 없습니다.");
                return false;
            }
        }

        // 8. 모든 관문 통과
        return true;
    }
	
}