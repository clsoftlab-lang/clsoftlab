package com.example.clsoftlab.dto.pay;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalcOrderRequestDto {
	
	private Long id;

	@NotBlank(message = "항목 코드는 필수입니다.")
    private String itemCode;

    @NotNull(message = "계산 순서는 필수입니다.")
    @Positive(message = "계산 순서는 양수여야 합니다.")
    private Integer order;

    @NotBlank(message = "계산 그룹은 필수입니다.")
    private String groupCode;

    private Long dependsOnId;
    private String note;
    
    @NotBlank(message = "사용 여부는 필수입니다.")
    @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'만 가능합니다.")
    private String useYn;
}
