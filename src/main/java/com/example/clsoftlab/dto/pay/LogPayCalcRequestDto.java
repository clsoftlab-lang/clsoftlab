package com.example.clsoftlab.dto.pay;

import java.math.BigDecimal;

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
public class LogPayCalcRequestDto {

	@NotBlank(message = "지급월은 필수입니다.")
    @Size(min = 6, max = 6, message = "지급월은 6자리(YYYYMM)여야 합니다.")
    private String payYm;

    @NotNull(message = "차수는 필수입니다.")
    private Integer seqNo;

    @NotBlank(message = "사번은 필수입니다.")
    private String empNo;

    @NotBlank(message = "항목 코드는 필수입니다.")
    private String itemCode;

    @NotNull(message = "계산 단계는 필수입니다.")
    private Integer stepNo;

    @Size(max = 255, message = "단계 설명은 최대 255자입니다.")
    private String stepDesc;

    private BigDecimal baseVal;

    @Size(max = 1000, message = "수식 정보는 최대 1000자입니다.")
    private String formula;

    @NotNull(message = "계산 결과(원시값)는 필수입니다.")
    private BigDecimal resultVal;

    private BigDecimal roundVal;

    @Size(max = 20, message = "절사 방식은 최대 20자입니다.")
    private String roundType;

    @Size(max = 20, message = "계산 출처는 최대 20자입니다.")
    private String calcSrc;

    @Size(max = 500, message = "비고는 최대 500자입니다.")
    private String note;
}
