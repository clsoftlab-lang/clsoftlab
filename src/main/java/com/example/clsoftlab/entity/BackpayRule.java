package com.example.clsoftlab.entity;

import java.math.BigDecimal;

import com.example.clsoftlab.dto.pay.BackpayRuleRequestDto;

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
@Table(name = "ZHR_BACKPAY_RULE")
public class BackpayRule extends BaseEntity{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RULE_ID")
    private Long ruleId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZITEM_CD", nullable = false)
    private PayItem appliedItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZBASE_ITEM", nullable = false)
    private PayItem baseItem;

    @Column(name = "ZRULE_TY", nullable = false, length = 10)
    private String ruleType; // 소급 방식 (FIXED, DIFF)

    @Column(name = "ZBACK_PC", precision = 5, scale = 2)
    private BigDecimal backPercent; // 소급 비율

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn;

    @Column(name = "ZNOTE", length = 500)
    private String note;
    
    public void update(BackpayRuleRequestDto dto) {
        this.ruleType = dto.getRuleType();
        this.backPercent = dto.getBackPercent();
        this.useYn = dto.getUseYn();
        this.note = dto.getNote();
    }
    
}
