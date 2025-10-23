package com.example.clsoftlab.entity;

import java.math.BigDecimal;

import com.example.clsoftlab.dto.pay.PayDetailUpdateDto;

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
@Table(name = "ZHR_PAY_DETAIL")
public class PayDetail extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DETAIL_ID")
    private Long id;

    @Column(name = "ZPAY_YM", length = 6, nullable = false)
    private String payYm;

    @Column(name = "ZSEQ_NO", nullable = false)
    private Integer seqNo; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZEMP_NO", referencedColumnName = "PERNR")
    private EmployeeMaster employeeMaster;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZITEM_CD", referencedColumnName = "ZITEM_CD")
    private PayItem payItem;

    @Column(name = "ZAMOUNT", precision = 18, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "ZBACK_YM", length = 6)
    private String backYm;

    @Column(name = "ZORIG_AMT", precision = 18, scale = 2)
    private BigDecimal origAmt;

    @Column(name = "ZNOTE", length = 500)
    private String note;
    
    public void update(PayDetailUpdateDto dto) {
    	this.amount = dto.getAmount();
    	this.backYm = dto.getBackYm();
    	this.origAmt = dto.getOrigAmt();
    	this.note = dto.getNote();
    }
}
