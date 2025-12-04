package com.example.clsoftlab.dto.hr;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeLangRequestDto {

private Long id;
	
    @NotBlank(message = "사번은 필수 입력 항목입니다.")
    @Size(max = 10, message = "사번은 최대 10자까지 입력 가능합니다.")
    private String pernr;

    @NotNull(message = "순번은 필수 입력 항목입니다.")
    private Integer seq;

    @NotBlank(message = "언어 코드는 필수 입력 항목입니다.")
    @Size(max = 20, message = "언어 코드는 최대 20자까지 입력 가능합니다.")
    private String langCode;

    @Size(max = 20, message = "시험 코드는 최대 20자까지 입력 가능합니다.")
    private String testCode;

    @Size(max = 50, message = "시험명은 최대 50자까지 입력 가능합니다.")
    private String testName;

    @Size(max = 20, message = "성적은 최대 20자까지 입력 가능합니다.")
    private String score;

    @NotNull(message = "취득일자는 필수 입력 항목입니다.")
    @PastOrPresent(message = "취득일자는 현재 또는 과거 날짜여야 합니다.")
    private LocalDate getDate;

    private LocalDate expDate;

    @Size(max = 10, message = "회화 수준은 최대 10자까지 입력 가능합니다.")
    private String speakLvl;

    @Size(max = 10, message = "독해 수준은 최대 10자까지 입력 가능합니다.")
    private String readLvl;

    @Size(max = 100, message = "첨부파일 ID는 최대 100자까지 입력 가능합니다.")
    private String attachId;

    @Size(max = 200, message = "비고는 최대 200자까지 입력 가능합니다.")
    private String remark;
    
}
