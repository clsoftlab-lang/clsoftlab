package com.example.clsoftlab.dto.common;

import jakarta.validation.constraints.Min;
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
public class CodeGroupRequestDto {

    @NotBlank(message = "코드 그룹 ID는 필수 입력 값입니다.")
    @Size(max = 20, message = "코드 그룹 ID는 20자 이내여야 합니다.")
    private String id;

    @NotBlank(message = "코드 그룹명은 필수 입력 값입니다.")
    @Size(max = 100, message = "코드 그룹명은 100자 이내여야 합니다.")
    private String groupName;

    @Pattern(regexp = "^[YN]$", message = "사용 여부는 Y 또는 N이어야 합니다.")
    private String useYn;

    @Min(value = 0, message = "정렬 순서는 0 이상이어야 합니다.")
    private Integer order;

    @Size(max = 300, message = "비고는 300자 이내여야 합니다.")
    private String remark;

    @Pattern(regexp = "^[YN]$", message = "시스템 여부는 Y 또는 N이어야 합니다.")
    private String isSystem;
}
