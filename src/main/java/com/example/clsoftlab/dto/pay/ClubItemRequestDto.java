package com.example.clsoftlab.dto.pay;

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
public class ClubItemRequestDto {

	@NotBlank(message = "회비 코드는 필수입니다.")
    @Size(max = 10, message = "회비 코드는 10자를 초과할 수 없습니다.")
    private String clubCode;

    @NotBlank(message = "회비명은 필수입니다.")
    @Size(max = 100, message = "회비명은 100자를 초과할 수 없습니다.")
    private String clubName;

    @NotNull(message = "금액은 필수입니다.")
    @Min(value = 0, message = "금액은 0 이상이어야 합니다.")
    private Long amount;

    @NotBlank(message = "사용 여부는 필수입니다.")
    @Pattern(regexp = "[YN]", message = "사용 여부는 'Y' 또는 'N'만 가능합니다.")
    private String useYn;

    @Size(max = 500, message = "설명은 500자를 초과할 수 없습니다.")
    private String note;
}
