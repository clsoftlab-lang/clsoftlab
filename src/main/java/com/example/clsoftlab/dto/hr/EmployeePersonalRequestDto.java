package com.example.clsoftlab.dto.hr;

import java.time.LocalDate;

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
public class EmployeePersonalRequestDto {

	@NotBlank(message = "사번은 필수 입력 항목입니다.")
    @Size(max = 10, message = "사번은 최대 10자까지 입력 가능합니다.")
    private String pernr;

    @NotNull(message = "입사일은 필수 입력 항목입니다.")
    private LocalDate joinDate;

    @NotBlank(message = "고용형태 코드는 필수 입력 항목입니다.")
    @Size(max = 4, message = "고용형태 코드는 최대 4자까지 입력 가능합니다.")
    private String empType;

    @NotBlank(message = "사업장 코드는 필수 입력 항목입니다.")
    @Size(max = 5, message = "사업장 코드는 최대 5자까지 입력 가능합니다.")
    private String locCode;

    @NotBlank(message = "부서 코드는 필수 입력 항목입니다.")
    @Size(max = 10, message = "부서 코드는 최대 10자까지 입력 가능합니다.")
    private String deptCode;

    @NotBlank(message = "직급 코드는 필수 입력 항목입니다.")
    @Size(max = 5, message = "직급 코드는 최대 5자까지 입력 가능합니다.")
    private String gradeCode;

    @Size(max = 5, message = "직책 코드는 최대 5자까지 입력 가능합니다.")
    private String posCode;

    @NotBlank(message = "고용상태 코드는 필수 입력 항목입니다.")
    @Size(max = 2, message = "고용상태 코드는 최대 2자까지 입력 가능합니다.")
    private String status;

    @Size(max = 10, message = "발령 기준 ID는 최대 10자까지 입력 가능합니다.")
    private String ruleId;

    private LocalDate effDate;

    @Size(max = 255, message = "비고는 최대 255자까지 입력 가능합니다.")
    private String remark;
}
