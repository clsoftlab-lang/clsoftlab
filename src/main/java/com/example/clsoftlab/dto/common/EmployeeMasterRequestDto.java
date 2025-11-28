package com.example.clsoftlab.dto.common;

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
public class EmployeeMasterRequestDto {

	@NotBlank(message = "사번은 필수 입력 값입니다.")
    @Size(max = 10, message = "사번은 10자 이내여야 합니다.")
    private String pernr;

    @NotBlank(message = "성명은 필수 입력 값입니다.")
    @Size(max = 50, message = "성명은 50자 이내여야 합니다.")
    private String name;

    @NotNull(message = "입사일자는 필수 입력 값입니다.")
    private LocalDate entryDate;
    
    private LocalDate retireDate;

    @NotBlank(message = "부서 코드는 필수 입력 값입니다.")
    @Size(max = 20, message = "부서 코드는 20자 이내여야 합니다.")
    private String deptCode;

    @NotBlank(message = "직급 코드는 필수 입력 값입니다.")
    @Size(max = 20, message = "직급 코드는 20자 이내여야 합니다.")
    private String rankCode;

    @NotBlank(message = "직무 코드는 필수 입력 값입니다.")
    @Size(max = 20, message = "직무 코드는 20자 이내여야 합니다.")
    private String dutyCode;

    @Size(max = 20, message = "재직 상태 코드는 20자 이내여야 합니다.")
    private String empStatus;
}
