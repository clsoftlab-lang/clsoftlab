package com.example.clsoftlab.dto.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuRequestDto {

	@NotBlank(message = "메뉴 ID는 필수 입력 값입니다.")
    @Size(min = 2, max = 20, message = "메뉴 ID는 2자 이상 20자 이하로 입력해주세요.")
    private String id;

    @Size(max = 20, message = "상위 메뉴 ID는 20자 이하여야 합니다.")
    private String upMenuId;

    @NotBlank(message = "메뉴명은 필수 입력 값입니다.")
    @Size(max = 100, message = "메뉴명은 100자 이하로 입력해주세요.")
    private String menuName;

    @Size(max = 200, message = "메뉴 URL은 200자 이하로 입력해주세요.")
    private String menuUrl;

    @Size(max = 50, message = "아이콘 클래스명은 50자 이하로 입력해주세요.")
    private String iconName;

    @PositiveOrZero(message = "정렬 순서는 0 이상의 숫자여야 합니다.")
    private Integer order;

    @Pattern(regexp = "^[YN]$", message = "사용 여부는 Y 또는 N이어야 합니다.")
    private String useYn;
}
