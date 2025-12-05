package com.example.clsoftlab.dto.hr;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRewardSimpleDto {

	private Long id;              // PK (수정/삭제용)
    private Integer seq;          // 정렬 순서
    private String rewardType;    // 포상 구분 (HR_REWARD_TYPE)
    private String rewardName;    // 포상명
    private String rewardOrg;     // 수여기관
    private LocalDate rewardDate; // 수여일자
    private Long rewardAmt;       // 포상 금액 (리스트에서 바로 확인)
}
