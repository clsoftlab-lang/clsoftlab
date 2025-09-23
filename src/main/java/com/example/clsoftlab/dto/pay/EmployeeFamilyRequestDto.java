package com.example.clsoftlab.dto.pay;

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
    @Size(max = 20, message = "사원번호는 최대 20자까지 입력 가능합니다.")
    private String empNo;

    @NotNull(message = "가족 순번은 필수 입력 항목입니다.")
    @Positive(message = "가족 순번은 양수여야 합니다.")
    private Integer familySeq;

    @NotBlank(message = "관계는 필수 입력 항목입니다.")
    @Size(max = 20, message = "관계는 최대 20자까지 입력 가능합니다.")
    private String familyType;

    @NotBlank(message = "가족 성명은 필수 입력 항목입니다.")
    @Size(max = 100, message = "가족 성명은 최대 100자까지 입력 가능합니다.")
    private String familyName;

    @NotNull(message = "생년월일은 필수 입력 항목입니다.")
    @PastOrPresent(message = "생년월일은 현재 또는 과거 날짜여야 합니다.")
    private LocalDate birth;

    @NotBlank(message = "성별은 필수 입력 항목입니다.")
    @Pattern(regexp = "[MF]", message = "성별은 'M' 또는 'F' 중 하나여야 합니다.")
    private String gender;

    @NotNull(message = "부양가족 여부는 필수입니다.")
    private String dependYn; 

    @NotNull(message = "수당 적용 여부는 필수입니다.")
    private String allowYn;

    @NotNull(message = "세금공제 대상 여부는 필수입니다.")
    private String taxYn;

    @NotNull(message = "장애 여부는 필수입니다.")
    private String disabledYn;

    @NotNull(message = "적용 시작일은 필수 입력 항목입니다.")
    private LocalDate fromDate;

    @NotNull(message = "적용 종료일은 필수 입력 항목입니다.")
    private LocalDate toDate;

    @Size(max = 500, message = "비고는 최대 500자까지 입력 가능합니다.")
    private String note;
}
