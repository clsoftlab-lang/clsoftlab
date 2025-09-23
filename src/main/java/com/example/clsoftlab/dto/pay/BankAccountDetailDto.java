package com.example.clsoftlab.dto.pay;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BankAccountDetailDto {

	private long id;
    private String empNo;
    private String accountType;
    private String bankCode;
    private String bankName;
    private String accountNo;
    private String accountHolder;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String useYn;
    private String note;
    
    public void setAccountNo(String accountNo) {
        this.accountNo = maskAccountNumber(accountNo);
    }

    private String maskAccountNumber(String accNo) {
        if (accNo == null || accNo.length() < 7) {
            return accNo;
        }
        int length = accNo.length();
        StringBuilder maskedAccount = new StringBuilder(accNo);

        // 계좌번호의 앞 3자리와 마지막 3자리를 제외한 부분을 마스킹
        for (int i = 3; i < length - 3; i++) {
            maskedAccount.setCharAt(i, '*');
        }

        return maskedAccount.toString();
    }
}
