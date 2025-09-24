package com.example.clsoftlab.entity;

import java.time.LocalDate;

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
@Table(name = "ZHR_PAY_CERTIFICATE")
public class PayCertificate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CERTIFICATE_ID")
    private Long id;

    @Column(name = "ZCERT_NO", nullable = false, length = 30)
    private String certNo;

    @Column(name = "ZYEAR", nullable = false, length = 4)
    private String year;

    @Column(name = "ZEMP_NO", nullable = false, length = 20)
    private String empNo;

    @Column(name = "ZPERIOD_TY", nullable = false, length = 20)
    private String periodType;

    @Column(name = "ZPERIOD_FROM", nullable = false)
    private LocalDate periodFrom;

    @Column(name = "ZPERIOD_TO", nullable = false)
    private LocalDate periodTo;

    @Column(name = "ZTOT_GROSS", nullable = false)
    private Long totalGross;

    @Column(name = "ZTOT_TAXABLE", nullable = false)
    private Long totalTaxable;

    @Column(name = "ZTOT_NONTAX", nullable = false)
    private Long totalNontax;

    @Column(name = "ZTOT_TAX", nullable = false)
    private Long totalTax;

    @Column(name = "ZTOT_LOCAL_TAX", nullable = false)
    private Long totalLocalTax;

    @Column(name = "ZTOT_INSURANCE", nullable = false)
    private Long totalInsurance;

    @Column(name = "ZINSR_DETAIL", length = 1000)
    private String insuranceDetail;

    @Column(name = "ZPAY_COUNT", nullable = false)
    private Integer payCount;

    @Column(name = "ZNOTE", length = 500)
    private String note;
}
