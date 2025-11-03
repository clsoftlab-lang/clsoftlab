package com.example.clsoftlab.dto.pay;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
public class PayDeductReasonRequestDto {

	private Long id;
	
	@NotBlank(message = "사번은 필수 입력 항목입니다.")
    private String empNo;

    @NotBlank(message = "급여월은 필수 입력 항목입니다.")
    @Pattern(regexp = "^\\d{6}$", message = "급여월은 YYYYMM 형식으로 입력해야 합니다.")
    private String payYm;

    @NotNull(message = "급여차수는 필수 입력 항목입니다.")
    @Positive(message = "급여차수는 1 이상의 양수여야 합니다.")
    private Integer seqNo;

    @NotBlank(message = "감액 항목 코드는 필수 입력 항목입니다.")
    private String itemCode;

    @PositiveOrZero(message = "감액 일수는 0 이상이어야 합니다.")
    private BigDecimal days;

    @PositiveOrZero(message = "감액 시간은 0 이상이어야 합니다.")
    private BigDecimal hours;

    @NotNull(message = "감액 금액은 필수 입력 항목입니다.")
    @PositiveOrZero(message = "감액 금액은 0 이상이어야 합니다.")
    private Long amount;

    @NotBlank(message = "사유 코드는 필수 입력 항목입니다.")
    private String reasonCode;

    @Size(max = 500, message = "비고는 최대 500자까지 입력할 수 있습니다.")
    private String note;
}
