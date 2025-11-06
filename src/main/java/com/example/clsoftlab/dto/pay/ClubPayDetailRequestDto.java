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
public class ClubPayDetailRequestDto {

	private Long id;
	
	@NotBlank(message = "사번(empNo)은 필수입니다.")
    @Size(max = 10)
    private String empNo; // EmployeeMaster PK

    @NotNull(message = "회비 항목 ID(clubId)는 필수입니다.")
    private Long clubId;  // ClubItem PK

    @NotBlank(message = "납부월(payYm)은 필수입니다.")
    @Pattern(regexp = "^\\d{6}$", message = "납부월은 6자리 숫자(YYYYMM) 형식이어야 합니다.")
    private String payYm;

    @NotNull(message = "납부금액(amount)은 필수입니다.")
    @Min(value = 1, message = "납부금액은 0보다 커야 합니다.")
    private Long amount;

    @Size(max = 500)
    private String note;
}
