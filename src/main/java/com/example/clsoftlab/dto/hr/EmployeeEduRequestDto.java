package com.example.clsoftlab.dto.hr;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEduRequestDto {

	private Long id;
	
	@NotBlank(message = "사번은 필수 입력 항목입니다.")
    @Size(max = 10, message = "사번은 최대 10자까지 입력 가능합니다.")
    private String pernr;

    @NotNull(message = "교육 순번은 필수 입력 항목입니다.")
    private Integer seq;

    @NotBlank(message = "교육과정명은 필수 입력 항목입니다.")
    @Size(max = 100, message = "교육과정명은 최대 100자까지 입력 가능합니다.")
    private String eduName;

    @NotBlank(message = "교육유형은 필수 입력 항목입니다.")
    @Size(max = 1, message = "교육유형은 한 글자만 입력 가능합니다. (I: 사내, E: 사외)")
    private String eduType;

    @NotBlank(message = "교육기관은 필수 입력 항목입니다.")
    @Size(max = 100, message = "교육기관은 최대 100자까지 입력 가능합니다.")
    private String eduOrg;

    @NotNull(message = "교육 시작일은 필수 입력 항목입니다.")
    private LocalDate startDate;

    @NotNull(message = "교육 종료일은 필수 입력 항목입니다.")
    private LocalDate endDate;

    @NotBlank(message = "수료 여부는 필수 입력 항목입니다.")
    @Size(max = 1, message = "수료 여부는 한 글자만 입력 가능합니다. (Y/N)")
    private String completeYn;

    @PositiveOrZero(message = "교육시간은 0 이상의 숫자여야 합니다.")
    private Integer hour;
    
    @Digits(integer = 11, fraction = 2, message = "교육비용은 정수부 11자리, 소수부 2자리까지 입력 가능합니다.")
    @PositiveOrZero(message = "교육비용은 0 이상의 숫자여야 합니다.")
    private BigDecimal cost;

    @Size(max = 100, message = "첨부파일 ID는 최대 100자까지 입력 가능합니다.")
    private String attachId;

    @Size(max = 200, message = "비고는 최대 200자까지 입력 가능합니다.")
    private String remark;
}
