package com.example.clsoftlab.dto.hr;

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
public class EvaluationDetailRequestDto {
	
	private Long id;

	@NotBlank(message = "사번은 필수 입력 항목입니다.")
    @Size(max = 10, message = "사번은 최대 10자까지 입력 가능합니다.")
    private String pernr;

    @NotBlank(message = "평가 연도는 필수 입력 항목입니다.")
    @Size(max = 4, message = "평가 연도는 4자로 입력해야 합니다.")
    private String year;

    @NotBlank(message = "평가 차수는 필수 입력 항목입니다.")
    @Size(max = 2, message = "평가 차수는 최대 2자까지 입력 가능합니다.")
    private String seq;
    
    @NotBlank(message = "평가 유형은 필수 입력 항목입니다.")
    @Size(max = 2, message = "평가 유형은 최대 2자까지 입력 가능합니다.")
    private String evType;

    @NotBlank(message = "평가 항목 코드는 필수 입력 항목입니다.")
    @Size(max = 10, message = "평가 항목 코드는 최대 10자까지 입력 가능합니다.")
    private String itemCode;

    @NotBlank(message = "평가 항목명은 필수 입력 항목입니다.")
    @Size(max = 100, message = "평가 항목명은 최대 100자까지 입력 가능합니다.")
    private String itemName;

    @NotNull(message = "점수는 필수 입력 항목입니다.")
    @Min(value = 0, message = "점수는 0 이상이어야 합니다.")
    private Integer point;

    @Size(max = 255, message = "항목별 코멘트는 최대 255자까지 입력 가능합니다.")
    private String comment;
}
