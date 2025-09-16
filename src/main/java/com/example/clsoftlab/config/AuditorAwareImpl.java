package com.example.clsoftlab.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;


// 감사용 설정 class (ex: created at, created by...)
public class AuditorAwareImpl implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		
		 // 실제 프로젝트에서는 Spring Security의 SecurityContextHolder를 사용해
        // 현재 로그인한 사용자의 ID를 가져옵니다.
        // 예: Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //     return Optional.of(auth.getName());

        // 지금은 임시로 "system"을 반환하도록 설정합니다.
        return Optional.of("system");
	}
}
