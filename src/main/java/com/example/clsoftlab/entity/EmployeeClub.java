package com.example.clsoftlab.entity;

import java.time.LocalDate;

import com.example.clsoftlab.dto.pay.EmployeeClubRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ZHR_EMP_CLUB")
public class EmployeeClub extends BaseEntity{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ZHR_EMP_CLUB_ID")
    private Long id;

    @Column(name = "ZEMP_NO", nullable = false, length = 50)
    private String empNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZCLUB_CD", nullable = false)
    private ClubItem clubItem;

    @Column(name = "ZFROM_DT", nullable = false)
    private LocalDate fromDate;

    @Column(name = "ZTO_DT", nullable = false)
    private LocalDate toDate;

    @Column(name = "ZPAY_YN", nullable = false, length = 1)
    private String payYn;

    @Column(name = "ZNOTE", length = 500)
    private String note;
    
    public void update(EmployeeClubRequestDto dto) {
    	this.fromDate = dto.getFromDate();
    	this.toDate = dto.getToDate();
    	this.payYn = dto.getPayYn();
    	this.note = dto.getNote();
    }

}
