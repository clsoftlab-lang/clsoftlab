package com.example.clsoftlab.dto.hr;

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
public class EvalDetailRequestDto {
	
	private Long id;

    @NotNull(message = "평가 결과 ID(시험지)는 필수입니다.")
    private Long resultId;

    @NotNull(message = "평가 항목 ID(문항)는 필수입니다.")
    private Long itemMasterId;

    @NotNull(message = "점수는 필수입니다.")
    @Min(value = 0, message = "점수는 0점 이상이어야 합니다.")
    @Max(value = 100, message = "점수는 100점을 초과할 수 없습니다.")
    private Integer itemScore;

    @Size(max = 1000, message = "코멘트는 1000자를 초과할 수 없습니다.")
    private String itemComment;
}
