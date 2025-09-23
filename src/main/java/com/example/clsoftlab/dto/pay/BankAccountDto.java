package com.example.clsoftlab.dto.pay;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
// 수정시 계좌번호 중복 확인용 dto
public class BankAccountDto {

	private long id;
	private String empNo;
	private String accountType;
	private String accountNo;
}
