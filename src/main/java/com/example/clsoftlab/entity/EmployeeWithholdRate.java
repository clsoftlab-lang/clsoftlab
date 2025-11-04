package com.example.clsoftlab.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.clsoftlab.dto.pay.EmployeeWithholdRateRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ZHR_EMP_WITHHOLD_RATE")
public class EmployeeWithholdRate extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ZWITHHOLD_PC", nullable = false, precision = 5, scale = 2)
    private BigDecimal withholdPc;

    @Column(name = "ZFROM_DT", nullable = false)
    private LocalDate fromDate;

    @Column(name = "ZTO_DT", nullable = false)
    private LocalDate toDate;

    @Column(name = "ZNOTE", length = 500)
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZEMP_NO", nullable = false) // 이 테이블의 FK 컬럼명
    private EmployeeMaster employee;
    
    @Version
    @Column(name = "VERSION")
    private Integer version;
    
    public void update (EmployeeWithholdRateRequestDto dto) {
    	this.withholdPc = dto.getWithholdPc();
    	this.fromDate = dto.getFromDate();
    	this.toDate = dto.getToDate();
    	this.note = dto.getNote();
    }
}
