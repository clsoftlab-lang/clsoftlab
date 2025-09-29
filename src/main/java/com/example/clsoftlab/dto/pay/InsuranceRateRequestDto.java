package com.example.clsoftlab.dto.pay;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
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
public class InsuranceRateRequestDto {

	@NotBlank(message = "보험 종류는 필수값입니다.")
    @Size(max = 10, message = "보험 종류는 최대 10자까지 입력 가능합니다.")
    private String insType;

    @NotNull(message = "적용 시작일은 필수값입니다.")
    private LocalDate fromDate;

    @NotNull(message = "적용 종료일은 필수값입니다.")
    private LocalDate toDate;

    @NotNull(message = "근로자 부담율은 필수값입니다.")
    @DecimalMin(value = "0.0", message = "근로자 부담율은 0 이상이어야 합니다.")
    private BigDecimal pcEmp;

    @NotNull(message = "사업주 부담율은 필수값입니다.")
    @DecimalMin(value = "0.0", message = "사업주 부담율은 0 이상이어야 합니다.")
    private BigDecimal pcCmp;

    @Size(max = 500, message = "비고는 최대 500자까지 입력 가능합니다.")
    private String note;

    @NotBlank(message = "사용 여부는 필수값입니다.")
    @Pattern(regexp = "[YN]", message = "사용 여부는 'Y' 또는 'N'만 가능합니다.")
    private String useYn;

}
