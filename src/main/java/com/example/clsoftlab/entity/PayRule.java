package com.example.clsoftlab.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.clsoftlab.dto.pay.PayRuleRequestDto;

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
@Table(name = "ZHR_PAY_RULE")
public class PayRule extends BaseEntity{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RULE_ID")
    private Long ruleId;

    @Column(name = "ZITEM_CD", nullable = false, length = 20)
    private String itemCode;

    @Column(name = "ZRULE_TY", nullable = false, length = 10)
    private String ruleType;

    @Column(name = "ZFORMULA", length = 2000)
    private String formula;

    @Column(name = "ZFIXED_VAL", precision = 18, scale = 2)
    private BigDecimal fixedValue;

    @Column(name = "ZFROM_DT", nullable = false)
    private LocalDate fromDate;

    @Column(name = "ZTO_DT", nullable = false)
    private LocalDate toDate;

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn;

    @Column(name = "ZNOTE", length = 500)
    private String note;

    // ZHR_PAY_ITEM 테이블과의 연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZITEM_CD", referencedColumnName = "ZITEM_CD", insertable = false, updatable = false)
    private PayItem payItem;
    
    public void update(PayRuleRequestDto dto) {
        this.ruleType = dto.getRuleType();
        this.formula = dto.getFormula();
        this.fixedValue = dto.getFixedValue();
        this.fromDate = dto.getFromDate();
        this.toDate = dto.getToDate();
        this.useYn = dto.getUseYn();
        this.note = dto.getNote();
    }
    
}
