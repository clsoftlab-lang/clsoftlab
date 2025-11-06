package com.example.clsoftlab.dto.pay;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClubPayDetailDto {

	private Long id;
    private String employeePernr; // EmployeeMaster PK
    private String employeeName;
    private Long clubId;  // ClubItem PK
    private String clubName;
    private String clubCode;
    private String payYm;
    private Long amount;
    private String note;
    private LocalDate createdAt;
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt.toLocalDate();
    }
}
