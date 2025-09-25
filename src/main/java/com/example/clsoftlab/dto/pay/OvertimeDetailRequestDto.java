package com.example.clsoftlab.dto.pay;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class OvertimeDetailRequestDto {

	@NotBlank(message = "사원번호는 필수입니다.")
    @Size(max = 50, message = "사원번호는 최대 50자까지 입력 가능합니다.")
    private String empNo;

    @NotNull(message = "근무일자는 필수입니다.")
    @PastOrPresent(message = "근무일자는 오늘 또는 과거 날짜만 입력 가능합니다.")
    private LocalDate date;

    @NotNull(message = "근로시간은 필수입니다.")
    @DecimalMin(value = "0.0", inclusive = false, message = "근로시간은 0보다 커야 합니다.")
    private BigDecimal hours;

    @NotBlank(message = "근로유형은 필수입니다.")
    @Pattern(regexp = "^(OVERTIME|NIGHT|HOLIDAY)$", message = "근로유형은 'OVERTIME', 'NIGHT', 'HOLIDAY' 중 하나여야 합니다.")
    private String type;

    @Size(max = 500, message = "비고는 최대 500자까지 입력 가능합니다.")
    private String note;
}
