package com.example.clsoftlab.dto.pay;

import java.math.BigDecimal;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
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
public class RetireAdjustRequestDto {
	
	private Long id;
	private String empNo;

	@NotNull(message = "퇴직 정산 ID(retireSummaryId)는 필수입니다.")
    private Long retireSummaryId;

    @NotBlank(message = "가감 유형(adjType)은 필수입니다.")
    @Size(max = 10, message = "가감 유형은 10자를 초과할 수 없습니다.")
    @Pattern(regexp = "EXTRA|DEDUCT", message = "가감 유형은 'EXTRA' 또는 'DEDUCT'만 허용됩니다.")
    private String adjType;

    @NotBlank(message = "가감 사유(adjReason)는 필수입니다.")
    @Size(max = 100, message = "가감 사유는 100자를 초과할 수 없습니다.")
    private String adjReason;

    @Min(value = 0, message = "가감 금액(adjAmount)은 0 이상이어야 합니다.")
    private BigDecimal adjAmount;
    
    @Size(max = 500, message = "계산 공식은 500자를 초과할 수 없습니다.")
    private String adjExpr;
    
    @Size(max = 1000, message = "비고는 1000자를 초과할 수 없습니다.")
    private String note;
    
    @AssertTrue(message = "금액(adjAmount) 또는 공식(adjExpr) 중 하나는 반드시 입력되어야 합니다.")
    public boolean isAmountOrExprPresent() {
        return (adjAmount != null) || (adjExpr != null && !adjExpr.isBlank());
    }
}
