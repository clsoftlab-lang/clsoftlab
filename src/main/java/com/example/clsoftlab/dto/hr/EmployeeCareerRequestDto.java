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
public class EmployeeCareerRequestDto {

	private Long id;
	
	@NotBlank(message = "사번은 필수 입력 항목입니다.")
    @Size(max = 10, message = "사번은 최대 10자까지 입력 가능합니다.")
    private String pernr;

    @NotNull(message = "경력 순번은 필수 입력 항목입니다.")
    private Integer seq;

    @NotBlank(message = "경력 구분은 필수 입력 항목입니다.")
    @Size(max = 1, message = "경력 구분은 'I' 또는 'E' 중 하나여야 합니다.")
    private String careerType;

    @NotBlank(message = "회사명은 필수 입력 항목입니다.")
    @Size(max = 100, message = "회사명은 최대 100자까지 입력 가능합니다.")
    private String compName;

    @Size(max = 100, message = "부서명은 최대 100자까지 입력 가능합니다.")
    private String dept;

    @NotBlank(message = "직무/직책은 필수 입력 항목입니다.")
    @Size(max = 100, message = "직무/직책은 최대 100자까지 입력 가능합니다.")
    private String position;

    @NotNull(message = "시작일은 필수 입력 항목입니다.")
    private LocalDate startDate;

    private LocalDate endDate;
    private Integer periodMonth;

    @Size(max = 100, message = "퇴사사유는 최대 100자까지 입력 가능합니다.")
    private String resignReason;

    @Size(max = 100, message = "첨부파일 ID는 최대 100자까지 입력 가능합니다.")
    private String attachId;

    @Size(max = 200, message = "비고는 최대 200자까지 입력 가능합니다.")
    private String remark;
}
