package com.example.clsoftlab.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.clsoftlab.dto.pay.BasePayRequestDto;

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
@Table(name = "ZHR_BASE_PAY")
public class BasePay extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAY_ID")
    private Long payId; // 급여이력 ID (PK)

    @Column(name = "ZEMP_NO", nullable = false, length = 20)
    private String empNo; // 사원번호

    @Column(name = "ZFROM_DT", nullable = false)
    private LocalDate fromDate; // 적용 시작일

    @Column(name = "ZTO_DT", nullable = false)
    private LocalDate toDate; // 적용 종료일

    @Column(name = "ZBASE_PAY", nullable = false, precision = 18, scale = 2)
    private BigDecimal basePay; // 기준급여

    @Column(name = "ZBASE_UNIT", nullable = false, length = 10)
    private String baseUnit; // 급여 단위

    @Column(name = "ZSTD_HOURS", nullable = false)
    private Integer standardHours; // 월 소정 근로시간

    @Column(name = "ZNOTE", length = 500)
    private String note; // 비고
    
    public void update (BasePayRequestDto dto) {
    	this.fromDate = dto.getFromDate();
    	this.toDate = dto.getToDate();
    	this.basePay = dto.getBasePay();
    	this.baseUnit = dto.getBaseUnit();
    	this.standardHours = dto.getStandardHours();
    	this.note = dto.getNote();
    }
}
