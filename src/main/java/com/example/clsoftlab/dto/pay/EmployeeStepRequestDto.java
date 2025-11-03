package com.example.clsoftlab.dto.pay;

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
public class EmployeeStepRequestDto {

	private Long id;
	
	@NotBlank(message = "사번은 필수입니다.")
    @Size(max = 10, message = "사번은 최대 10자입니다.")
    private String pernr; // 사번

    @NotBlank(message = "직군 코드는 필수입니다.")
    @Size(max = 20, message = "직군 코드는 최대 20자입니다.")
    private String gradeCode; // 직군 코드 (ZGRADE_CD)

    @NotNull(message = "호봉 번호는 필수입니다.")
    private Integer stepNo; // 호봉 번호 (ZSTEP_NO)

    @NotNull(message = "적용 시작일은 필수입니다.")
    private LocalDate fromDate; // 적용 시작일 (ZFROM_DT)

    @NotNull(message = "적용 종료일은 필수입니다.")
    private LocalDate toDate; // 적용 종료일 (ZTO_DT)

    @Size(max = 500, message = "비고는 최대 500자입니다.")
    private String note;
}
