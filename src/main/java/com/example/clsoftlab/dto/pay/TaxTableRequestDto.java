package com.example.clsoftlab.dto.pay;

import java.math.BigDecimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class TaxTableRequestDto {

	@NotNull(message = "연도는 필수입니다.")
	@Digits(integer = 4, fraction = 0, message = "연도는 4자리 정수여야 합니다.")
    private Integer year;

    @NotNull(message = "가족 수는 필수입니다.")
    @Min(value = 0, message = "가족 수는 0 이상이어야 합니다.")
    private Integer familyCount;

    @NotNull(message = "과세 기준 소득은 필수입니다.")
    @PositiveOrZero(message = "소득은 0 이상이어야 합니다.")
    private BigDecimal incomeAmount;

    @Digits(integer = 3, fraction = 2, message = "세율은 소수점 2자리까지 허용됩니다.")
    private BigDecimal taxPercent;

    @Digits(integer = 3, fraction = 2, message = "세율은 소수점 2자리까지 허용됩니다.")
    private BigDecimal localPercent;

    private BigDecimal totalTax;

    @NotEmpty(message = "사용여부는 필수입니다.")
    private String useYn;

    private String note;
}
