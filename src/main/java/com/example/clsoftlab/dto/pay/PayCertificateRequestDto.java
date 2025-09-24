package com.example.clsoftlab.dto.pay;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class PayCertificateRequestDto {

	@NotBlank(message = "귀속 연도는 필수입니다.")
    @Size(min = 4, max = 4, message = "연도는 4자리여야 합니다.")
    @Pattern(regexp = "^\\d{4}$", message = "연도는 숫자 4자리 형식이어야 합니다.")
    private String year;

    @NotBlank(message = "사원번호는 필수입니다.")
    @Size(max = 20, message = "사원번호는 최대 20자입니다.")
    private String empNo;

    @NotBlank(message = "기간 유형은 필수입니다.")
    @Size(max = 20, message = "기간 유형은 최대 20자입니다.")
    private String periodType;

    @NotNull(message = "기간 시작일은 필수입니다.")
    private LocalDate periodFrom;

    @NotNull(message = "기간 종료일은 필수입니다.")
    private LocalDate periodTo;
    
    @NotNull(message = "총 지급액은 필수입니다.")
    @PositiveOrZero(message = "총 지급액은 0 이상이어야 합니다.")
    private Long totalGross;

    @NotNull(message = "과세 대상 금액은 필수입니다.")
    @PositiveOrZero(message = "과세 대상 금액은 0 이상이어야 합니다.")
    private Long totalTaxable;

    @NotNull(message = "비과세 금액은 필수입니다.")
    @PositiveOrZero(message = "비과세 금액은 0 이상이어야 합니다.")
    private Long totalNontax;

    @NotNull(message = "소득세는 필수입니다.")
    @PositiveOrZero(message = "소득세는 0 이상이어야 합니다.")
    private Long totalTax;

    @NotNull(message = "지방소득세는 필수입니다.")
    @PositiveOrZero(message = "지방소득세는 0 이상이어야 합니다.")
    private Long totalLocalTax;

    @NotNull(message = "4대보험 총액은 필수입니다.")
    @PositiveOrZero(message = "4대보험 총액은 0 이상이어야 합니다.")
    private Long totalInsurance;
    
    @Size(max = 1000, message = "보험 상세는 최대 1000자입니다.")
    private String insuranceDetail;

    @NotNull(message = "급여 횟수는 필수입니다.")
    @PositiveOrZero(message = "급여 횟수는 0 이상이어야 합니다.")
    private Integer payCount;
    
    @Size(max = 500, message = "비고는 최대 500자입니다.")
    private String note;
}
