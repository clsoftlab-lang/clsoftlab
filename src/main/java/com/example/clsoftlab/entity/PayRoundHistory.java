package com.example.clsoftlab.entity;

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
@Table(name = "ZHR_PAY_ROUND_HISTORY")
public class PayRoundHistory extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROUND_HISTORY_ID")
    private Long id;

    @Column(name = "ZEMP_NO", nullable = false, length = 20)
    private String empNo;

    @Column(name = "ZPAY_YM", nullable = false, length = 6)
    private String payYm;

    @Column(name = "ZITEM_CD", nullable = false, length = 20)
    private String itemCode;

    @Column(name = "ZRAW_AMT", nullable = false)
    private Long rawAmount;

    @Column(name = "ZROUND_TY", nullable = false, length = 20)
    private String roundType;

    @Column(name = "ZROUND_AMT", nullable = false)
    private Long roundAmount;

    @Column(name = "ZDIFF_AMT", nullable = false)
    private Long diffAmount;

    @Column(name = "ZSRC_CD", nullable = false, length = 20)
    private String sourceCode;

    @Column(name = "ZNOTE", length = 500)
    private String note;
}
