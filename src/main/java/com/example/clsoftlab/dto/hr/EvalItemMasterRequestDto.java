package com.example.clsoftlab.dto.hr;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class EvalItemMasterRequestDto {

	private Long id;

    @NotBlank(message = "템플릿 ID는 필수입니다.")
    private String templateId;

    @NotBlank(message = "평가 항목 명은 필수입니다.")
    @Size(max = 200, message = "항목 명은 200자를 초과할 수 없습니다.")
    private String itemName;

    @Size(max = 1000, message = "설명은 1000자를 초과할 수 없습니다.")
    private String itemDesc;

    @NotNull(message = "배점은 필수입니다.")
    @Min(value = 0, message = "배점은 0점 이상이어야 합니다.")
    @Max(value = 100, message = "배점은 100점을 초과할 수 없습니다.") // 일반적인 배점 기준
    private Integer weight;

    @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
    private String useYn;

    @NotNull(message = "정렬 순서는 필수입니다.")
    @Min(value = 0, message = "정렬 순서는 0 이상이어야 합니다.")
    private Integer order;
}
