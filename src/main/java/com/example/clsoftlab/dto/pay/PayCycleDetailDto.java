package com.example.clsoftlab.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayCycleDetailDto {

    private String jobGroup;
    private int startDay;
    private int endDay;
    private int payDay;
    private String useYn;
    private String note;
}
