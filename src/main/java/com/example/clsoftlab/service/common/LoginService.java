package com.example.clsoftlab.service.common;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.clsoftlab.dto.common.LoginRequestDto;
import com.example.clsoftlab.dto.common.LoginResultDto;
import com.example.clsoftlab.dto.common.RolePermDetailDto;
import com.example.clsoftlab.dto.common.SysMenuDetailDto;
import com.example.clsoftlab.dto.common.UserAccountResponseDto;
import com.example.clsoftlab.entity.LoginLog;
import com.example.clsoftlab.entity.SysMenu;
import com.example.clsoftlab.entity.UserAccount;
import com.example.clsoftlab.repository.common.LoginLogRepository;
import com.example.clsoftlab.repository.common.RolePermRepository;
import com.example.clsoftlab.repository.common.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final UserAccountRepository userAccountRepository;
	private final LoginLogRepository loginLogRepository;
	private final RolePermRepository rolePermRepository;
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	
	// 로그인
	public LoginResultDto login (LoginRequestDto dto, String clientIp, String userAgent) {
		
		String logResult = LoginLog.RESULT_FAIL; // 기본값 실패
        String failReason = null;
        
        try {
            // 1. ID 존재 확인
            UserAccount user = userAccountRepository.findById(dto.getUserId())
            		.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디"));

            // 2. 계정 상태 체크 (잠김, 사용중지 등)
            if ("Y".equals(user.getIsLocked())) {
                failReason = "계정 잠금 상태";
                throw new IllegalStateException("비밀번호 5회 오류로 잠긴 계정입니다. 관리자에게 문의하세요.");
            }
            if ("N".equals(user.getIsUse())) {
                failReason = "휴면/미사용 계정";
                throw new IllegalStateException("사용이 중지된 계정입니다.");
            }

            // 3. 비밀번호 검증
            if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                // [실패 로직]
                user.increaseFailCount(); // 실패 카운트 +1
                failReason = "비밀번호 불일치";

                // 5회 이상 실패 시 잠금
                if (user.getLoginFailCnt() >= 5) {
                    user.lockAccount(); // isLocked = "Y"
                    failReason = "비밀번호 5회 오류(잠금처리)";
                    userAccountRepository.save(user); // 변경사항 저장
                    throw new IllegalStateException("비밀번호 5회 오류! 계정이 잠금 처리되었습니다.");
                }

                userAccountRepository.save(user); // 실패 카운트 저장
                // 보안상 ID/PW 중 무엇이 틀렸는지 알려주지 않음
                throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
            }

            // 4. [성공 로직]
            user.resetFailCount(); // 실패 카운트 초기화
            user.setLastLoginDate(LocalDateTime.now()); // 마지막 접속일
            userAccountRepository.save(user);

            logResult = LoginLog.RESULT_SUCCESS;
            
            List<RolePermDetailDto> rolePermList = rolePermRepository.findPermsByRoleId(user.getRoleId()).stream()
            		.map(RolePermDetailDto::fromEntity)
            		.toList();
            
            Map<String, RolePermDetailDto> permMap = new HashMap<>();
            for (RolePermDetailDto p : rolePermList) {
                if (p.getMenuUrl() != null) {
                    permMap.put(p.getMenuUrl(), p);
                }
            }
            UserAccountResponseDto userDto = modelMapper.map(user, UserAccountResponseDto.class);
            
            List<SysMenuDetailDto> menuTree = buildMenuTree(user.getRoleId());
            
            return LoginResultDto.builder()
                    .userDto(userDto)
                    .permMap(permMap)
                    .menuTree(menuTree)
                    .build();
            		
        } catch (Exception e) {
            // 예외 발생 시(로그인 실패) 로그에 사유 기록
            if (failReason == null) {
                // "존재하지 않는 아이디" 같은 예외 메시지를 로그 사유로 사용
                failReason = e.getMessage(); 
            }
            
            // 컨트롤러로 예외를 다시 던져서 클라이언트에게 응답
            // (ID 없음일 경우 메시지 보안 처리)
            if ("존재하지 않는 아이디".equals(e.getMessage())) {
                 throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.");
            }
            throw e; 

        } finally {
            // 5. [로그 저장] 성공/실패 여부와 관계없이 무조건 실행
        	LoginLog log = LoginLog.builder()
                    .loginId(dto.getUserId())
                    .loginType(LoginLog.TYPE_GENERAL)
                    .ipAddress(clientIp)
                    .userAgent(userAgent)
                    .loginResult(logResult)
                    .failReason(failReason)
                    // loginDate는 @CreationTimestamp로 자동 생성됨
                    .build();
            System.out.println(failReason);
            loginLogRepository.save(log);
            
        }
	}
	
	private List<SysMenuDetailDto> buildMenuTree(String roleId) {
        // 1. 해당 Role이 볼 수 있는 모든 메뉴 조회 (RolePermRepository에 쿼리 추가 필요)
        // 쿼리: SELECT m FROM RolePerm rp JOIN rp.menu m WHERE rp.role.id = :roleId AND rp.read = 'Y' ORDER BY m.order ASC
        List<SysMenu> allowedMenus = rolePermRepository.findMenusByRoleId(roleId);
        
        // 2. DTO 변환
        List<SysMenuDetailDto> dtoList = allowedMenus.stream()
                .map(SysMenuDetailDto::fromEntityFlat)
                .toList();

        // 3. 트리 조립 (Map 활용)
        Map<String, SysMenuDetailDto> lookup = new HashMap<>();
        List<SysMenuDetailDto> roots = new ArrayList<>();

        // 3-1. 맵에 담기 (ID로 객체 찾기 위함)
        for (SysMenuDetailDto dto : dtoList) {
            lookup.put(dto.getId(), dto);
            // 자식 리스트 초기화 (중요)
            dto.setChildren(new ArrayList<>());
        }

        // 3-2. 부모-자식 연결
        for (SysMenuDetailDto dto : dtoList) {
            if (dto.getUpMenuId() == null) {
                // 부모가 없으면 최상위(Root) 메뉴
                roots.add(dto);
            } else {
                // 부모가 있으면 부모의 children에 추가
                SysMenuDetailDto parent = lookup.get(dto.getUpMenuId());
                if (parent != null) {
                    parent.getChildren().add(dto);
                } else {
                    // 부모 권한은 없는데 자식 권한만 있는 경우 (고아), 그냥 Root로 취급하거나 정책 결정
                    // 여기서는 Root로 처리
                    roots.add(dto); 
                }
            }
        }
        
        // 정렬은 DB에서 해왔으므로 패스
        return roots;
    }
}
