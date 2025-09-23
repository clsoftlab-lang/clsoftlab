package com.example.clsoftlab.dto.pay;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayRoundHistoryRequestDto {

	@NotBlank(message = "사원번호는 필수입니다.")
    private String empNo;

    @NotBlank(message = "급여월은 필수입니다.")
    @Pattern(regexp = "^\\d{6}$", message = "급여월은 YYYYMM 형식이어야 합니다.")
    private String payYm;

    @NotBlank(message = "급여 항목 코드는 필수입니다.")
    private String itemCode;

    @NotNull(message = "절사 전 원금액은 필수입니다.")
    @PositiveOrZero(message = "절사 전 원금액은 0 이상이어야 합니다.")
    private Long rawAmount;

    @NotBlank(message = "절사 방식은 필수입니다.")
    private String roundType;

    @NotNull(message = "절사 후 금액은 필수입니다.")
    @PositiveOrZero(message = "절사 후 금액은 0 이상이어야 합니다.")
    private Long roundAmount;

    @NotBlank(message = "절사 기준 항목 코드는 필수입니다.")
    private String sourceCode;

    private String note;
}
