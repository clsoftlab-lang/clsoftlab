package com.example.clsoftlab.entity;

import java.math.BigDecimal;

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
@Table(name = "ZHR_LOG_PAY_CALC")
public class LogPayCalc extends BaseEntityWithOutUpdate {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOG_ID")
    private Long id;

    @Column(name = "ZPAY_YM", nullable = false, length = 6)
    private String payYm;

    @Column(name = "ZSEQ_NO", nullable = false)
    private Integer seqNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZEMP_NO", referencedColumnName = "PERNR", nullable = false)
    private EmployeeMaster employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZITEM_CD", nullable = false)
    private PayItem payItem;

    @Column(name = "ZSTEP_NO", nullable = false)
    private Integer stepNo;

    @Column(name = "ZSTEP_DESC", length = 255)
    private String stepDesc;

    @Column(name = "ZBASE_VAL", precision = 20, scale = 4)
    private BigDecimal baseVal;

    @Column(name = "ZFORMULA", length = 1000)
    private String formula;

    @Column(name = "ZRESULT_VAL", nullable = false, precision = 20, scale = 4)
    private BigDecimal resultVal;

    @Column(name = "ZROUND_VAL", precision = 18, scale = 2)
    private BigDecimal roundVal;

    @Column(name = "ZROUND_TY", length = 20)
    private String roundType;

    @Column(name = "ZCALC_SRC", length = 20)
    private String calcSrc;

    @Column(name = "ZNOTE", length = 500)
    private String note;
    
}
