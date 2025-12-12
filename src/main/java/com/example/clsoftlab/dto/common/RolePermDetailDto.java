package com.example.clsoftlab.dto.common;

import com.example.clsoftlab.entity.RolePerm;
import com.example.clsoftlab.entity.SysMenu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermDetailDto {

	private Long id; // 매핑 테이블의 PK (수정/삭제 시 필요)
    private String roleId;   // 예: ADMIN
    private String roleName; // 예: 슈퍼 관리자
    private String menuId;     // 예: SYS_USER
    private String menuName;   // 예: 사용자 관리
    private String menuUrl;    // 예: /sys/user
    private String upMenuId;   // 상위 메뉴 ID
    private String upMenuName; // 상위 메뉴명 (예: 시스템 관리)
    private String read;  // 조회 권한 (Y/N)
    private String write; // 쓰기 권한 (Y/N)
    
    public static RolePermDetailDto fromEntity(RolePerm entity) {
        if (entity == null) return null;

        // 부모 메뉴 정보 추출 (Null Safe)
        String parentId = null;
        String parentName = null;
        
        SysMenu menu = entity.getMenu();
        if (menu != null && menu.getUpMenu() != null) {
            parentId = menu.getUpMenu().getId();
            parentName = menu.getUpMenu().getMenuName();
        }

        return RolePermDetailDto.builder()
                .id(entity.getId())
                .roleId(entity.getRole().getId())
                .roleName(entity.getRole().getRoleName())
                .menuId(menu.getId())
                .menuName(menu.getMenuName())
                .menuUrl(menu.getMenuUrl())
                .upMenuId(parentId)
                .upMenuName(parentName)
                .read(entity.getRead())
                .write(entity.getWrite())
                .build();
    }
}
