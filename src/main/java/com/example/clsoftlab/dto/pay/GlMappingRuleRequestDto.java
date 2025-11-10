package com.example.clsoftlab.dto.pay;

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
public class GlMappingRuleRequestDto {
	
	private Long id;

	@NotBlank(message = "항목 코드는 필수 입력 항목입니다.")
    @Size(max = 20, message = "항목 코드는 최대 50자까지 입력 가능합니다.")
    private String itemCode;

    @NotBlank(message = "사업장 코드는 필수 입력 항목입니다.")
    @Size(max = 10, message = "사업장 코드는 최대 10자까지 입력 가능합니다.")
    private String bizCode;

    @NotBlank(message = "코스트센터는 필수 입력 항목입니다.")
    @Size(max = 20, message = "코스트센터는 최대 20자까지 입력 가능합니다.")
    private String costCntr;

    @NotBlank(message = "회계 계정 코드는 필수 입력 항목입니다.")
    @Size(max = 30, message = "회계 계정 코드는 최대 30자까지 입력 가능합니다.")
    private String glAccount;

    @Size(max = 500, message = "설명은 최대 500자까지 입력 가능합니다.")
    private String note;

    @NotBlank(message = "사용 여부는 필수 입력 항목입니다.")
    @Pattern(regexp = "[YN]", message = "사용 여부는 'Y' 또는 'N'만 입력 가능합니다.")
    private String useYn;
}
