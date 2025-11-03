package com.example.clsoftlab.entity;

import java.math.BigDecimal;

import com.example.clsoftlab.dto.pay.PayDeductReasonRequestDto;

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
@Table(name = "ZHR_PAY_DEDUCT_REASON")
public class PayDeductReason extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAY_DEDUCT_REASON_ID")
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY) // 성능을 위해 LAZY 로딩 권장
    @JoinColumn(name = "ZEMP_NO", nullable = false, referencedColumnName = "PERNR") // DB의 FK 컬럼(ZEMP_NO) 지정
    private EmployeeMaster employee;
    
    @Column(name = "ZPAY_YM", nullable = false, length = 6)
    private String payYm;
    
    @Column(name = "ZSEQ_NO", nullable = false)
    private Integer seqNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZITEM_CD", nullable = false, referencedColumnName = "ZITEM_CD")
    private PayItem payItem;

    @Column(name = "ZDAYS", precision = 5, scale = 2)
    private BigDecimal days;

    @Column(name = "ZHOURS", precision = 5, scale = 2)
    private BigDecimal hours;

    @Column(name = "ZAMOUNT", nullable = false)
    private Long amount;

    @Column(name = "ZREASON_CD", nullable = false, length = 20)
    private String reasonCode;

    @Column(name = "ZNOTE", length = 500)
    private String note;
    
    @Version
    @Column(name = "VERSION")
    private Long version;
    
    public void update (PayDeductReasonRequestDto dto) {
    	this.days = dto.getDays();
    	this.hours = dto.getHours();
    	this.amount = dto.getAmount();
    	this.reasonCode = dto.getReasonCode();
    	this.note = dto.getNote();
    }
}
