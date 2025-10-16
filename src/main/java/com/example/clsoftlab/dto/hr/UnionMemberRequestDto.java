package com.example.clsoftlab.dto.hr;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnionMemberRequestDto {

	private Long id;
	
	@NotBlank(message = "사번은 필수 입력 항목입니다.")
    @Size(max = 10, message = "사번은 최대 10자까지 입력 가능합니다.")
    private String pernr;

    @NotNull(message = "가입일은 필수 입력 항목입니다.")
    @PastOrPresent(message = "가입일은 현재 또는 과거 날짜여야 합니다.")
    private LocalDate joinDate;

    @NotBlank(message = "가입 여부는 필수 입력 항목입니다.")
    @Pattern(regexp = "[YN]", message = "가입 여부는 'Y' 또는 'N'만 가능합니다.")
    private String unionYn;

    @PastOrPresent(message = "탈퇴일은 현재 또는 과거 날짜여야 합니다.")
    private LocalDate leaveDate;

    @NotBlank(message = "노조 유형은 필수 입력 항목입니다.")
    @Size(max = 5, message = "노조 유형 코드는 최대 5자까지 입력 가능합니다.")
    private String unionType;

    @NotBlank(message = "회비 코드는 필수 입력 항목입니다.")
    @Size(max = 10, message = "회비 코드는 최대 10자까지 입력 가능합니다.")
    private String clubCode;

    @NotNull(message = "회비는 필수 입력 항목입니다.")
    @PositiveOrZero(message = "회비는 0보다 커야 합니다.")
    private BigDecimal fee;

    @NotBlank(message = "자동공제 여부는 필수 입력 항목입니다.")
    @Pattern(regexp = "[YN]", message = "자동공제 여부는 'Y' 또는 'N'만 가능합니다.")
    private String autoDeduct;

    @Size(max = 255, message = "비고는 최대 255자까지 입력 가능합니다.")
    private String remark;
}
