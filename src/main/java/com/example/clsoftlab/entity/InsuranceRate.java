package com.example.clsoftlab.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.clsoftlab.dto.pay.InsuranceRateRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "ZHR_INS_RATE")
public class InsuranceRate extends BaseEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RATE_ID")
    private Long id;

    @Column(name = "ZINS_TYPE", nullable = false, length = 10)
    private String insType;

    @Column(name = "ZFROM_DT", nullable = false)
    private LocalDate fromDate;

    @Column(name = "ZTO_DT", nullable = false)
    private LocalDate toDate;

    @Column(name = "ZPC_EMP", nullable = false, precision = 8, scale = 5)
    private BigDecimal pcEmp;

    @Column(name = "ZPC_CMP", nullable = false, precision = 8, scale = 5)
    private BigDecimal pcCmp;

    @Column(name = "ZNOTE", length = 500)
    private String note;

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn;	

    public void update (InsuranceRateRequestDto dto) {
    	this.fromDate = dto.getFromDate();
    	this.toDate = dto.getToDate();
    	this.pcEmp = dto.getPcEmp();
    	this.pcCmp = dto.getPcCmp();
    	this.note = dto.getNote();
    	this.useYn = dto.getUseYn();
    }
}
