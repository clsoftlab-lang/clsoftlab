package com.example.clsoftlab.dto.common;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResultDto {
	private UserAccountResponseDto userDto;          // 사용자 정보 (세션 LOGIN_USER 용)
    private Map<String, RolePermDetailDto> permMap;  // 권한 지도 (세션 USER_PERMS 용)
    private List<SysMenuDetailDto> menuTree;
}
