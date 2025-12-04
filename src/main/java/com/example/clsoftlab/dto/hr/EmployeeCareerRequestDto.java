package com.example.clsoftlab.dto.hr;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class EmployeeCareerRequestDto {

	private Long id;
	
    @NotBlank(message = "사번은 필수 입력 항목입니다.")
    @Size(max = 10, message = "사번은 최대 10자까지 입력 가능합니다.")
    private String pernr;

    @NotNull(message = "경력 순번은 필수 입력 항목입니다.")
    private Integer seq;

    @NotBlank(message = "근무 형태는 필수 입력 항목입니다.")
    @Size(max = 20, message = "근무 형태 코드는 최대 20자까지 입력 가능합니다.")
    private String careerType;

    @NotBlank(message = "회사명은 필수 입력 항목입니다.")
    @Size(max = 100, message = "회사명은 최대 100자까지 입력 가능합니다.")
    private String companyName;

    @Size(max = 100, message = "부서명은 최대 100자까지 입력 가능합니다.")
    private String deptName;

    @Size(max = 50, message = "직위/직급은 최대 50자까지 입력 가능합니다.")
    private String jobRank;

    @Size(max = 100, message = "담당 직무는 최대 100자까지 입력 가능합니다.")
    private String jobDuty;

    @Size(max = 500, message = "업무 상세 내용은 최대 500자까지 입력 가능합니다.")
    private String jobDesc;

    @NotNull(message = "입사일은 필수 입력 항목입니다.")
    private LocalDate startDate;

    @NotNull(message = "퇴사일은 필수 입력 항목입니다.")
    private LocalDate endDate;

    private Long lastSalary;

    @Min(value = 0, message = "인정 비율은 0 이상이어야 합니다.")
    @Max(value = 100, message = "인정 비율은 100 이하이어야 합니다.")
    private Integer recogRatio;

    @Size(max = 200, message = "퇴사사유는 최대 200자까지 입력 가능합니다.")
    private String resignReason;

    @Size(max = 100, message = "첨부파일 ID는 최대 100자까지 입력 가능합니다.")
    private String attachId;

    @Size(max = 200, message = "비고는 최대 200자까지 입력 가능합니다.")
    private String remark;
}
