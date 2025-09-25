package com.example.clsoftlab.dto.pay;

import java.time.LocalDate;

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
public class EmployeeClubRequestDto {


    @NotBlank(message = "사원 번호는 필수입니다.")
    @Size(max = 50, message = "사원 번호는 최대 50자까지 입력 가능합니다.")
    private String empNo;

    @NotBlank(message = "회비 코드는 필수입니다.")
    @Size(max = 10, message = "회비 코드는 최대 10자까지 입력 가능합니다.")
    private String clubCode;

    @NotNull(message = "적용 시작일은 필수입니다.")
    private LocalDate fromDate;

    @NotNull(message = "적용 종료일은 필수입니다.")
    private LocalDate toDate;

    @NotBlank(message = "납부 여부는 필수입니다.")
    @Pattern(regexp = "[YN]", message = "납부 여부는 'Y' 또는 'N'만 가능합니다.")
    private String payYn;

    @Size(max = 500, message = "비고는 최대 500자까지 입력 가능합니다.")
    private String note;
}
