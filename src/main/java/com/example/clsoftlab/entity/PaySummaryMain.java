package com.example.clsoftlab.entity;

import com.example.clsoftlab.dto.pay.PaySummaryMainRequestDto;

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
@Table(name = "ZHR_PAY_SUMMARY_MAIN")
public class PaySummaryMain extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZEMP_NO", referencedColumnName = "PERNR", nullable = false)
    private EmployeeMaster employee; // 사원 마스터 엔티티 (가정)

    @Column(name = "ZPAY_YM", nullable = false, length = 6)
    private String payYm;

    @Column(name = "ZSEQ_NO", nullable = false)
    private Integer seqNo;

    @Column(name = "ZTOTAL_PAY", nullable = false)
    private Long totalPay;

    @Column(name = "ZTOTAL_DEDUCT", nullable = false)
    private Long totalDeduct;

    @Column(name = "ZTOTAL_REAL", nullable = false)
    private Long totalReal;

    @Column(name = "ZIS_FINAL", nullable = false, length = 1)
    private String isFinal;

    @Column(name = "ZNOTE", length = 500)
    private String note;
    
    @Version
    @Column(name = "VERSION")
    private Long version;
    
    public void update (PaySummaryMainRequestDto dto) {
    	this.seqNo = dto.getSeqNo();
    	this.totalPay = dto.getTotalPay();
    	this.totalDeduct = dto.getTotalDeduct();
    	this.totalReal = dto.getTotalReal();
    	this.isFinal = dto.getIsFinal();
    	this.note = dto.getNote();
    }
}
