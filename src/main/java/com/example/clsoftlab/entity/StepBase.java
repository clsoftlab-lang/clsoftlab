package com.example.clsoftlab.entity;

import java.time.LocalDate;

import com.example.clsoftlab.dto.pay.StepBaseRequestDto;

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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ZHR_STEP_BASE")
public class StepBase extends BaseEntity{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STEP_BASE_ID")
    private Long id; // 호봉 기준 ID (PK)

    @Column(name = "ZGRADE_CD", nullable = false, length = 20)
    private String gradeCode; // 직군 코드

    @Column(name = "ZSTEP_NO", nullable = false)
    private Integer stepNo; // 호봉 번호

    @Column(name = "ZBASE_PAY", nullable = false)
    private Long basePay; // 기준급여

    @Column(name = "ZBASE_UNIT", nullable = false, length = 10)
    private String baseUnit; // 기준 단위

    @Column(name = "ZFROM_DT", nullable = false)
    private LocalDate fromDate; // 유효 시작일

    @Column(name = "ZTO_DT", nullable = false)
    private LocalDate toDate; // 유효 종료일

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn = "Y"; // 사용 여부 (Y/N)

    @Column(name = "ZNOTE", length = 500)
    private String note; // 비고 (설명)
	
    public void update (StepBaseRequestDto dto) {
    	this.basePay = dto.getBasePay();
    	this.baseUnit = dto.getBaseUnit();
    	this.fromDate = dto.getFromDate();
    	this.toDate = dto.getToDate();
    	this.useYn = dto.getUseYn();
    	this.note = dto.getNote();
    }
}
