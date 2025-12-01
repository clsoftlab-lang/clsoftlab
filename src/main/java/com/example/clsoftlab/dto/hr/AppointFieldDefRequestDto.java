package com.example.clsoftlab.dto.hr;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AppointFieldDefRequestDto {

	private Long id; 

    @NotBlank(message = "발령 유형은 필수입니다.")
    @Size(max = 20, message = "발령 유형은 20자 이하여야 합니다.")
    private String ruleType; // ZRULE_TYPE

    @NotBlank(message = "항목 코드는 필수입니다.")
    @Size(max = 20, message = "항목 코드는 20자 이하여야 합니다.")
    private String fieldCode; // ZFIELD_CD

    @NotBlank(message = "항목 명칭은 필수입니다.")
    @Size(max = 100, message = "항목 명칭은 100자 이하여야 합니다.")
    private String fieldName; // ZFIELD_NM

    @NotBlank(message = "데이터 타입은 필수입니다.")
    @Pattern(regexp = "^(STRING|NUMBER|DATE|CODE)$", message = "데이터 타입은 STRING, NUMBER, DATE, CODE 중 하나여야 합니다.")
    private String dataType = "STRING"; // ZDATA_TYPE

    @Pattern(regexp = "^[YN]$", message = "필수 여부는 Y 또는 N이어야 합니다.")
    private String required = "N"; // ZREQUIRED

    @NotNull(message = "출력 순서는 필수입니다.")
    private Integer order = 0; // ZORDER
}
