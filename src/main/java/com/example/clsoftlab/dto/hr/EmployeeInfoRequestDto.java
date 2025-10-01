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
public class EmployeeInfoRequestDto {

    @NotNull(message = "입사일은 필수 항목입니다.")
    private LocalDate hireDate;

    @NotBlank(message = "고용형태 코드는 필수 항목입니다.")
    @Size(max = 10)
    private String empType;

    @NotBlank(message = "근무형태 코드는 필수 항목입니다.")
    @Size(max = 10)
    private String workType;

    @NotBlank(message = "사업장 코드는 필수 항목입니다.")
    @Size(max = 10)
    private String bizCode;

    @NotBlank(message = "부서 코드는 필수 항목입니다.")
    @Size(max = 10)
    private String deptCode;

    @NotBlank(message = "직책 코드는 필수 항목입니다.")
    @Size(max = 10)
    private String posCode;

    @NotBlank(message = "직급 코드는 필수 항목입니다.")
    @Size(max = 10)
    private String gradeCode;

    @NotNull(message = "현 부서 입사일은 필수 항목입니다.")
    private LocalDate deptJoin;

    @NotBlank(message = "근무상태 코드는 필수 항목입니다.")
    @Size(max = 2)
    private String empStatus;

    @NotNull(message = "최종 발령일자는 필수 항목입니다.")
    private LocalDate lastApptDate;

}
