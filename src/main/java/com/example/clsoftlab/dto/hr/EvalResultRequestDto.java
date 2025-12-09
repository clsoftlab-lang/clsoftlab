package com.example.clsoftlab.dto.hr;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvalResultRequestDto {
	
	private Long id;

    @NotBlank(message = "템플릿 ID는 필수입니다.")
    private String templateId;

    @NotBlank(message = "평가 단계는 필수입니다.")
    @Size(max = 10, message = "평가 단계 코드는 10자를 초과할 수 없습니다.")
    private String evalStep; // 예: 300, 400

    @NotBlank(message = "피평가자(대상) 사번은 필수입니다.")
    private String targetEmpId;

    @NotBlank(message = "평가자 사번은 필수입니다.")
    private String evalEmpId;

    @Min(value = 0, message = "총점은 0점 이상이어야 합니다.")
    @Max(value = 100, message = "총점은 100점을 초과할 수 없습니다.")
    private Integer totalScore;

    @Size(max = 2000, message = "종합 의견은 2000자를 초과할 수 없습니다.")
    private String comment;

    // 상태: T(임시저장), C(제출) - 값이 없으면 T로 간주
    @Pattern(regexp = "^[TC]$", message = "상태는 'T'(임시저장) 또는 'C'(제출)만 가능합니다.")
    private String status;
}
