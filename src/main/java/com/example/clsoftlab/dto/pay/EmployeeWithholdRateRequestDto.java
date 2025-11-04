package com.example.clsoftlab.dto.pay;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeWithholdRateRequestDto {

	private Long id;
	
	@NotBlank(message = "사번은 필수입니다.")
    @Size(max = 10, message = "사번은 최대 10자입니다.")
    private String empNo;

    @NotNull(message = "원천징수율은 필수입니다.")
    @DecimalMin(value = "0.00", message = "원천징수율은 0.00 이상이어야 합니다.")
    @DecimalMax(value = "999.99", message = "원천징수율은 999.99 이하여야 합니다.")
    private BigDecimal withholdPc;

    @NotNull(message = "적용 시작일은 필수입니다.")
    private LocalDate fromDate;

    @NotNull(message = "적용 종료일은 필수입니다.")
    private LocalDate toDate;

    @Size(max = 500, message = "비고는 최대 500자입니다.")
    private String note;
}
