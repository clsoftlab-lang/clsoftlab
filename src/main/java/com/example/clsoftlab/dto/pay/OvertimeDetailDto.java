package com.example.clsoftlab.dto.pay;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OvertimeDetailDto {

	private Long id;
    private String employeeName;
    private String employeePernr;
    private LocalDate date;
    private BigDecimal hours;
    private String type;
    private String note;
    private LocalDateTime createdAt;
}
