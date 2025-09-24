package com.example.clsoftlab.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubItemDetailDto {

    private String clubCode;
    private String clubName;
    private Long amount;
    private String useYn;
    private String note;
}
