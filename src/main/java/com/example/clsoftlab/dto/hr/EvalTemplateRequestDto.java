package com.example.clsoftlab.dto.hr;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class EvalTemplateRequestDto {

	private String templateId;

    @NotBlank(message = "평가 템플릿 명은 필수입니다.")
    @Size(max = 100, message = "템플릿 명은 100자를 초과할 수 없습니다.")
    private String templateName;

    @NotBlank(message = "평가 연도는 필수입니다.")
    @Size(min = 4, max = 4, message = "연도는 4자리여야 합니다.")
    private String year;

    @NotBlank(message = "평가 유형은 필수입니다.")
    @Size(max = 10, message = "평가 유형 코드는 10자를 초과할 수 없습니다.")
    private String evType; // 예: RG, SP

    @Size(max = 1, message = "상태 코드는 1자리여야 합니다.")
    private String status;

    @NotNull(message = "시작일은 필수입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private LocalDate startDate;

    @NotNull(message = "종료일은 필수입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private LocalDate endDate;
}
