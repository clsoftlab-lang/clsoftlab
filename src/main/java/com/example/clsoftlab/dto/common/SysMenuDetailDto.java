package com.example.clsoftlab.dto.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
public class SysMenuDetailDto {

	private String id;
    private String upMenuId;   // 상위 메뉴 ID (수정 시 select box 선택값)
    private String upMenuName; // 상위 메뉴명 (화면 표시용)
    private String menuName;
    private String menuUrl;
    private String iconName;
    private Integer order;
    private String useYn;
    @Builder.Default
    private List<SysMenuDetailDto> children = new ArrayList<>();
    
    public static SysMenuDetailDto fromEntity(SysMenu entity) {
        if (entity == null) return null;

        // 부모 메뉴 정보 추출 (Null Safe)
        String parentId = null;
        String parentName = null;
        if (entity.getUpMenu() != null) {
            parentId = entity.getUpMenu().getId();
            parentName = entity.getUpMenu().getMenuName();
        }

        return SysMenuDetailDto.builder()
                .id(entity.getId())
                .upMenuId(parentId)
                .upMenuName(parentName)
                .menuName(entity.getMenuName())
                .menuUrl(entity.getMenuUrl())
                .iconName(entity.getIconName())
                .order(entity.getOrder())
                .useYn(entity.getUseYn())
                
                .children(entity.getChildren() != null ? 
                        entity.getChildren().stream()
                              .map(SysMenuDetailDto::fromEntity) // 재귀 호출 (자기 자신 호출)
                              .collect(Collectors.toList()) 
                        : new ArrayList<>()).build();
    }
    
    
    /**
     *  로그인/권한 체크용 (자식 제외, 평면 변환)
     * - LoginService에서 수동으로 트리를 조립할 때 사용
     * - entity.getChildren()을 절대 호출하지 않음 (N+1 방지)
     */
    public static SysMenuDetailDto fromEntityFlat(SysMenu entity) {
        if (entity == null) return null;

        String parentId = null;
        String parentName = null;
        if (entity.getUpMenu() != null) {
            parentId = entity.getUpMenu().getId();
            parentName = entity.getUpMenu().getMenuName();
        }

        return SysMenuDetailDto.builder()
                .id(entity.getId())
                .upMenuId(parentId)
                .upMenuName(parentName)
                .menuName(entity.getMenuName())
                .menuUrl(entity.getMenuUrl())
                .iconName(entity.getIconName())
                .order(entity.getOrder())
                .useYn(entity.getUseYn())
                
                // [핵심] 자식은 비워둠 (Service에서 수동으로 채울 것이므로)
                .children(new ArrayList<>()) 
                .build();
    }
}
