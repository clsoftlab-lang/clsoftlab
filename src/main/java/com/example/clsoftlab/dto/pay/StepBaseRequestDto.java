package com.example.clsoftlab.dto.pay;

import java.time.LocalDate;

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
public class StepBaseRequestDto {

	@NotBlank(message = "직군 코드는 필수입니다.")
    @Size(max = 20, message = "직군 코드는 20자를 초과할 수 없습니다.")
    private String gradeCode;

    @NotNull(message = "호봉 번호는 필수입니다.")
    @Min(value = 1, message = "호봉 번호는 1 이상이어야 합니다.")
    private Integer stepNo;

    @NotNull(message = "기준급여는 필수입니다.")
    @Min(value = 0, message = "기준급여는 0 이상이어야 합니다.")
    private Long basePay;

    @NotNull(message = "기준 단위는 필수입니다.")
    @Pattern(regexp = "^(MONTHLY|YEARLY)$", message = "기준 단위는 'MONTHLY' 또는 'YEARLY'만 가능합니다.")
    private String baseUnit; // 

    @NotNull(message = "유효 시작일은 필수입니다.")
    private LocalDate fromDate;

    @NotNull(message = "유효 종료일은 필수입니다.")
    private LocalDate toDate;

    @NotBlank(message = "사용 여부는 필수입니다.")
    @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'만 가능합니다.")
    private String useYn;

    @Size(max = 500, message = "비고는 500자를 초과할 수 없습니다.")
    private String note;
}
