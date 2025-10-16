package com.example.clsoftlab.dto.hr;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnionMemberDetailDto {

	private Long id;
    private String pernr;
    private LocalDate joinDate;
    private String unionYn;
    private LocalDate leaveDate;
    private String unionType;
    private String clubCode;
    private BigDecimal fee;
    private String autoDeduct;
    private String remark;
}
