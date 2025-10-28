package com.example.clsoftlab.dto.pay;

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
public class PaySummaryMainRequestDto {

	private Long id;
	
	@NotBlank(message = "사번은 필수입니다.")
    @Size(max = 10, message = "사번은 최대 10자입니다.")
    private String empNo;

    @NotBlank(message = "급여월은 필수입니다.")
    @Size(min = 6, max = 6, message = "급여월은 6자리(YYYYMM)여야 합니다.")
    private String payYm;

    @NotNull(message = "정산차수는 필수입니다.")
    private Integer seqNo;

    @NotNull(message = "총지급액은 필수입니다.")
    private Long totalPay;

    @NotNull(message = "총공제액은 필수입니다.")
    private Long totalDeduct;

    @NotNull(message = "실지급액은 필수입니다.")
    private Long totalReal;

    @NotBlank(message = "확정여부는 필수입니다.")
    @Pattern(regexp = "[YN]", message = "확정여부는 'Y' 또는 'N'이어야 합니다.")
    private String isFinal;

    @Size(max = 500, message = "메모는 최대 500자입니다.")
    private String note;
}
