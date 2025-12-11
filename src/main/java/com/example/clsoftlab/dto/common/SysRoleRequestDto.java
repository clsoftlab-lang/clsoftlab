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
public class SysRoleRequestDto {

	@NotBlank(message = "권한 ID는 필수 입력 값입니다.")
    @Size(min = 2, max = 20, message = "권한 ID는 2자 이상 20자 이하로 입력해주세요.")
    private String id; 

    @NotBlank(message = "권한명은 필수 입력 값입니다.")
    @Size(max = 100, message = "권한명은 100자 이하로 입력해주세요.")
    private String roleName;

    @Size(max = 300, message = "권한 설명은 300자 이하로 입력해주세요.")
    private String roleDesc;

    @PositiveOrZero(message = "정렬 순서는 0 이상의 숫자여야 합니다.")
    private Integer order;

    @Pattern(regexp = "^[YN]$", message = "사용 여부는 Y 또는 N이어야 합니다.")
    private String useYn;
}
