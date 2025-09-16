package com.example.clsoftlab.dto.pay;

import java.math.BigDecimal;

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
public class BackpayRuleRequestDto {

	@NotBlank(message = "적용 항목 코드는 필수입니다.")
    @Size(max = 20, message = "적용 항목 코드는 20자를 초과할 수 없습니다.")
    private String appliedItemCode;

    @NotBlank(message = "기준 항목 코드는 필수입니다.")
    @Size(max = 20, message = "기준 항목 코드는 20자를 초과할 수 없습니다.")
    private String baseItemCode;

    @NotBlank(message = "소급 방식은 필수입니다.")
    @Pattern(regexp = "FIXED|DIFF", message = "소급 방식은 'FIXED' 또는 'DIFF'만 가능합니다.")
    private String ruleType;

    // FIXED 방식일 때만 값이 필요하므로, DTO에서는 @NotNull을 사용하지 않고
    // 서비스 계층에서 비즈니스 로직으로 검증하는 것이 더 유연합니다.
    private BigDecimal backPercent;

    @NotBlank(message = "사용 여부는 필수입니다.")
    @Pattern(regexp = "Y|N", message = "사용 여부는 'Y' 또는 'N'만 가능합니다.")
    private String useYn;

    @Size(max = 500, message = "비고는 500자를 초과할 수 없습니다.")
    private String note;
}
