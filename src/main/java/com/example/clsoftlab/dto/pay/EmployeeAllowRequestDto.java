package com.example.clsoftlab.dto.pay;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAllowRequestDto {

	@NotBlank(message = "사번은 필수입니다.")
    private String empNo; // EmployeeMaster의 ID (PERNR)

    @NotBlank(message = "항목 코드는 필수입니다.")
    private String itemCode; // PayItem의 ID (ZITEM_CD)

    @NotNull(message = "적용 시작일은 필수입니다.")
    private LocalDate fromDate;

    @NotNull(message = "적용 종료일은 필수입니다.")
    private LocalDate toDate;

    @NotNull(message = "수당 금액은 필수입니다.")
    @Min(value = 0, message = "수당 금액은 0 이상이어야 합니다.")
    private Long allowAmt;

    @NotBlank(message = "사용 여부는 필수입니다.")
    @Pattern(regexp = "[YN]", message = "사용 여부는 Y 또는 N만 가능합니다.")
    private String useYn;

    private String note;
}
