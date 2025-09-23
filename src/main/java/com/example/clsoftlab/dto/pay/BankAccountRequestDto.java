package com.example.clsoftlab.dto.pay;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BankAccountRequestDto {

	@NotBlank(message = "사원번호는 필수입니다.")
    private String empNo;

    @NotBlank(message = "계좌유형은 필수입니다.")
    @Pattern(regexp = "^(MAIN|SUB|LOAN|RETIRE)$", message = "계좌유형은 MAIN, SUB, LOAN, RETIRE 중 하나여야 합니다.")
    private String accountType;

    @NotBlank(message = "은행코드는 필수입니다.")
    private String bankCode;

    @NotBlank(message = "은행명은 필수입니다.")
    private String bankName;

    @NotBlank(message = "계좌번호는 필수입니다.")
    private String accountNo;

    @NotBlank(message = "예금주는 필수입니다.")
    private String accountHolder;

    @NotNull(message = "사용 시작일은 필수입니다.")
    private LocalDate fromDate;

    @NotNull(message = "사용 종료일은 필수입니다.")
    private LocalDate toDate;

    @NotBlank(message = "사용여부는 필수입니다.")
    @Pattern(regexp = "^[YN]$", message = "사용여부는 Y 또는 N 이어야 합니다.")
    private String useYn;

    private String note;
    
    public void setAccountNo(String accountNo) {
        // 정규식을 사용하여 숫자를 제외한 모든 문자를 제거
        this.accountNo = accountNo.replaceAll("[^0-9]", "");
    }
}
