package com.example.clsoftlab.dto.pay;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class RetireSummaryRequestDto {

	private Long id;
	
	@NotBlank(message = "사번(pernr)은 필수입니다.")
    private String pernr;

    @NotNull(message = "입사일자는 필수입니다.")
    private LocalDate entryDate;

    @NotNull(message = "퇴직일자는 필수입니다.")
    private LocalDate retireDate;

    @NotNull(message = "근속년수는 필수입니다.")
    @PositiveOrZero(message = "근속년수는 0 이상이어야 합니다.")
    private BigDecimal serviceYears;

    @NotNull(message = "평균임금은 필수입니다.")
    @PositiveOrZero(message = "평균임금은 0 이상이어야 합니다.")
    private BigDecimal avgSalary;

    @NotNull(message = "퇴직금은 필수입니다.")
    @PositiveOrZero(message = "퇴직금은 0 이상이어야 합니다.")
    private BigDecimal retirePay;

    @NotNull(message = "소득세는 필수입니다.")
    @PositiveOrZero(message = "소득세는 0 이상이어야 합니다.")
    private BigDecimal taxAmount;

    @NotNull(message = "실지급액은 필수입니다.")
    @PositiveOrZero(message = "실지급액은 0 이상이어야 합니다.")
    private BigDecimal finalPay;

    @Size(max = 1000, message = "설명은 1000자를 초과할 수 없습니다.")
    private String note;
    
}
