package com.example.clsoftlab.dto.pay;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasePayRequestDto {

	@NotBlank(message = "사원번호는 필수입니다.")
    private String empNo;

    @NotNull(message = "적용 시작일은 필수입니다.")
    private LocalDate fromDate;

    @NotNull(message = "적용 종료일은 필수입니다.")
    private LocalDate toDate;

    @NotNull(message = "기준급여는 필수입니다.")
    @PositiveOrZero(message = "기준급여는 0 이상이어야 합니다.")
    private BigDecimal basePay;

    @NotNull(message = "급여 단위는 필수입니다.")
    private String baseUnit;

    @NotNull(message = "월 소정 근로시간은 필수입니다.")
    @Positive(message = "월 소정 근로시간은 0보다 커야 합니다.")
    private Integer standardHours;

    private String note;
}
