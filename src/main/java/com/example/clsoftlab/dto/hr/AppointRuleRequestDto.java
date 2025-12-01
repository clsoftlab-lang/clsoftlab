package com.example.clsoftlab.dto.hr;

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
public class AppointRuleRequestDto {

	@NotBlank(message = "발령 기준 ID는 필수입니다.")
    @Size(max = 10, message = "ID는 10자 이하여야 합니다.")
    private String id; // ZRULE_ID

    @NotBlank(message = "발령 기준명은 필수입니다.")
    @Size(max = 100, message = "발령 기준명은 100자 이하여야 합니다.")
    private String ruleName; // ZRULE_NM

    @NotBlank(message = "발령 유형은 필수입니다.")
    @Size(max = 20, message = "발령 유형은 20자 이하여야 합니다.")
    private String ruleType; // ZRULE_TYPE

    @Size(max = 200, message = "설명은 200자 이하여야 합니다.")
    private String condDesc; // ZCOND_DESC

    @Pattern(regexp = "^[YN]$", message = "사용 여부는 Y 또는 N이어야 합니다.")
    private String useYn = "Y"; // ZUSE_YN (기본값 Y)
}
