package com.example.clsoftlab.entity;

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
@Table(name = "ZHR_PAY_ROUND_HISTORY")
public class PayRoundHistory extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROUND_HISTORY_ID")
    private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZEMP_NO", nullable = false, referencedColumnName = "PERNR")
    private EmployeeMaster employee;

    @Column(name = "ZPAY_YM", nullable = false, length = 6)
    private String payYm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZITEM_CD", nullable = false, referencedColumnName = "ZITEM_CD")
    private PayItem payItem;

    @Column(name = "ZRAW_AMT", nullable = false)
    private Long rawAmount;

    @Column(name = "ZROUND_TY", nullable = false, length = 20)
    private String roundType;

    @Column(name = "ZROUND_AMT", nullable = false)
    private Long roundAmount;

    @Column(name = "ZDIFF_AMT", nullable = false)
    private Long diffAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZSRC_CD", nullable = false, referencedColumnName = "ZITEM_CD")
    private PayItem sourcePayItem;

    @Column(name = "ZNOTE", length = 500)
    private String note;
    
    @Version
    @Column(name = "VERSION")
    private Long version;
}
