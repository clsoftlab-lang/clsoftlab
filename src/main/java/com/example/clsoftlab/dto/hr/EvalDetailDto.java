package com.example.clsoftlab.dto.hr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvalDetailDto {

    private Long id;
    private Long itemMstId;      // 문항 고유 ID
    private String itemName;     // 문항명 (예: 업무 성과)
    private String itemDesc;     // 문항 설명 (예: 목표 대비 달성률...)
    private Integer weight;      // 배점 (만점 기준)
    private Integer order;       // 정렬 순서 (몇 번째 문제인가)
    private Integer itemScore;   // 획득 점수 (point -> itemScore)
    private String itemComment;  // 의견/근거 (comment -> itemComment)
}
