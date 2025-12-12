package com.example.clsoftlab.dto.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolePermRequestDto {

	private Long id;

    @NotBlank(message = "권한 ID는 필수 입력 값입니다.")
    @Size(max = 20)
    private String roleId;

    @NotBlank(message = "메뉴 ID는 필수 입력 값입니다.")
    @Size(max = 20)
    private String menuId;

    @Pattern(regexp = "^[YN]$", message = "조회 권한은 Y 또는 N이어야 합니다.")
    private String read;

    @Pattern(regexp = "^[YN]$", message = "쓰기 권한은 Y 또는 N이어야 합니다.")
    private String write;
}
