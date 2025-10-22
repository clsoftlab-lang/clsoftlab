package com.example.clsoftlab.dto.pay;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
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
public class PayDetailRequestDto {

	private Long id;
	
	@NotBlank(message = "지급월은 필수입니다.")
	@Pattern(regexp = "^\\d{4}-\\d{2}$", message = "지급월 형식이 올바르지 않습니다. (YYYY-MM)")
    private String payYm;

    @NotNull(message = "차수는 필수입니다.")
    private Integer seqNo;

    @NotBlank(message = "사번은 필수입니다.")
    private String empNo; 

    @NotBlank(message = "항목 코드는 필수입니다.")
    private String itemCode; 

    @NotNull(message = "지급액은 필수입니다.")
    @Digits(integer = 16, fraction = 2, message = "지급액이 형식(정수 16, 소수 2)에 맞지 않습니다.")
    private BigDecimal amount;

    @Pattern(regexp = "^(\\d{4}-\\d{2})?$", message = "소급월 형식이 올바르지 않습니다. (YYYY-MM)")
    private String backYm;

    @Digits(integer = 16, fraction = 2, message = "원금이 형식(정수 16, 소수 2)에 맞지 않습니다.")
    private BigDecimal origAmt; 

    @Size(max = 500, message = "비고는 500자를 초과할 수 없습니다.")
    private String note;
}
