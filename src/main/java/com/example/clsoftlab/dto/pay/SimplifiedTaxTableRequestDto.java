package com.example.clsoftlab.dto.pay;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
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
public class SimplifiedTaxTableRequestDto {

	private Long id;
	
	@NotBlank(message = "기준 연도는 필수입니다.")
    @Pattern(regexp = "\\d{4}", message = "기준 연도는 4자리 숫자여야 합니다.")
    private String year;

    @NotNull(message = "공제 대상 가족 수는 필수입니다.")
    @Min(value = 0, message = "공제 대상 가족 수는 0 이상이어야 합니다.")
    private Integer familyCount; 

    @NotNull(message = "월 소득 기준 금액은 필수입니다.")
    @Min(value = 0, message = "월 소득 기준 금액은 0 이상이어야 합니다.")
    private Long incomeAmt; 

    @DecimalMin(value = "0.0", message = "소득세율은 0.0 이상이어야 합니다.")
    @DecimalMax(value = "100.0", message = "소득세율은 100.0 이하이어야 합니다.")
    @Digits(integer = 3, fraction = 2, message = "소득세율은 정수 3자리, 소수 2자리까지 유효합니다.")
    private BigDecimal taxPc; 

    @DecimalMin(value = "0.0", message = "지방소득세율은 0.0 이상이어야 합니다.")
    @DecimalMax(value = "100.0", message = "지방소득세율은 100.0 이하이어야 합니다.")
    @Digits(integer = 3, fraction = 2, message = "지방소득세율은 정수 3자리, 소수 2자리까지 유효합니다.")
    private BigDecimal localPc; 

    @Min(value = 0, message = "총 세액은 0 이상이어야 합니다.")
    private Long totalTax; 

    @NotBlank(message = "사용 여부는 필수입니다.")
    @Pattern(regexp = "[YN]", message = "사용 여부는 'Y' 또는 'N' 이어야 합니다.")
    private String useYn; 

    @Size(max = 500, message = "비고는 500자를 초과할 수 없습니다.")
    private String note; 
}
