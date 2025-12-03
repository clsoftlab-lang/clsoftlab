package com.example.clsoftlab.dto.hr;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFamilyRequestDto {

	@NotBlank(message = "사원번호는 필수 입력 항목입니다.")
    @Size(max = 10, message = "사원번호는 최대 10자까지 입력 가능합니다.") 
    private String pernr;

    @NotNull(message = "가족 순번은 필수 입력 항목입니다.")
    @Positive(message = "가족 순번은 양수여야 합니다.")
    private Integer familySeq;

    @NotBlank(message = "관계는 필수 입력 항목입니다.")
    @Size(max = 10, message = "관계 코드는 최대 10자까지 입력 가능합니다.") 
    private String familyType;

    @NotBlank(message = "가족 성명은 필수 입력 항목입니다.")
    @Size(max = 100, message = "가족 성명은 최대 100자까지 입력 가능합니다.")
    private String familyName;

    @NotBlank(message = "주민등록번호는 필수 입력 항목입니다.")
    @Size(max = 20, message = "주민등록번호 길이가 올바르지 않습니다.") 
    private String ssn; 

    @NotNull(message = "생년월일은 필수 입력 항목입니다.")
    @PastOrPresent(message = "생년월일은 현재 또는 과거 날짜여야 합니다.")
    private LocalDate birth;

    @NotBlank(message = "성별은 필수 입력 항목입니다.")
    @Pattern(regexp = "^[MF]$", message = "성별은 'M' 또는 'F'여야 합니다.")
    private String gender;

    @Size(max = 10, message = "직업 구분 코드는 최대 10자입니다.")
    private String jobType;

    @Size(max = 10, message = "학력 구분 코드는 최대 10자입니다.")
    private String schoolType;

    @Size(max = 100, message = "학교명은 최대 100자입니다.")
    private String schoolName;

    @NotNull(message = "동거 여부는 필수입니다.")
    @Pattern(regexp = "^[YN]$", message = "동거 여부는 'Y' 또는 'N'이어야 합니다.")
    private String liveYn;
    
    @NotNull(message = "부양가족 여부는 필수입니다.")
    @Pattern(regexp = "^[YN]$", message = "부양가족 여부는 'Y' 또는 'N'이어야 합니다.")
    private String dependYn; 

    @NotNull(message = "수당 적용 여부는 필수입니다.")
    @Pattern(regexp = "^[YN]$", message = "수당 적용 여부는 'Y' 또는 'N'이어야 합니다.")
    private String allowYn;

    @NotNull(message = "세금공제 대상 여부는 필수입니다.")
    @Pattern(regexp = "^[YN]$", message = "세금공제 대상 여부는 'Y' 또는 'N'이어야 합니다.")
    private String taxYn;

    @NotNull(message = "장애 여부는 필수입니다.")
    @Pattern(regexp = "^[YN]$", message = "장애 여부는 'Y' 또는 'N'이어야 합니다.")
    private String disabledYn;

    @Size(max = 20, message = "연락처는 최대 20자입니다.")
    private String phone;

    @NotNull(message = "적용 시작일은 필수 입력 항목입니다.")
    private LocalDate fromDate;

    @NotNull(message = "적용 종료일은 필수 입력 항목입니다.") 
    private LocalDate toDate;

    @Size(max = 500, message = "비고는 최대 500자까지 입력 가능합니다.")
    private String note;
}
