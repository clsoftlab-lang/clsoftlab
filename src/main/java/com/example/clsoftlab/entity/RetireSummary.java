package com.example.clsoftlab.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.clsoftlab.dto.pay.RetireSummaryRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "ZHR_RETIRE_SUMMARY")
public class RetireSummary extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERNR", referencedColumnName = "PERNR", nullable = false)
    private EmployeeMaster employee;

    @Column(name = "ENTRY_DT", nullable = false)
    private LocalDate entryDate;

    @Column(name = "RETIRE_DT", nullable = false)
    private LocalDate retireDate;

    @Column(name = "ZSERVICE_YRS", nullable = false, precision = 10, scale = 2)
    private BigDecimal serviceYears;

    @Column(name = "ZAVG_SALARY", nullable = false, precision = 18, scale = 2)
    private BigDecimal avgSalary;

    @Column(name = "ZRETIRE_PAY", nullable = false, precision = 18, scale = 2)
    private BigDecimal retirePay;

    @Column(name = "ZTAX_AMOUNT", nullable = false, precision = 18, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "ZFINAL_PAY", nullable = false, precision = 18, scale = 2)
    private BigDecimal finalPay;

    @Column(name = "ZNOTE", length = 1000)
    private String note;
    
    @Version
    @Column(name = "VERSION")
    private Long version;
    
    public void update (RetireSummaryRequestDto dto) {
    	this.entryDate = dto.getEntryDate();
    	this.retireDate = dto.getRetireDate();
    	this.serviceYears = dto.getServiceYears();
    	this.avgSalary = dto.getAvgSalary();
    	this.retirePay = dto.getRetirePay();
    	this.taxAmount = dto.getTaxAmount();
    	this.finalPay = dto.getFinalPay();
    	this.note = dto.getNote();
    }
}
