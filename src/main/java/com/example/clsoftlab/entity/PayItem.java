package com.example.clsoftlab.entity;

import java.math.BigDecimal;

import com.example.clsoftlab.dto.pay.PayItemRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "ZHR_PAY_ITEM")
public class PayItem extends BaseEntity {

	@Id
    @Column(name = "ZITEM_CD", length = 20)
    private String itemCode;

    @Column(name = "ZITEM_NM", nullable = false, length = 100)
    private String itemName;

    @Column(name = "ZITEM_TY", nullable = false, length = 20)
    private String itemType;

    @Column(name = "ZTAX_YN", nullable = false, length = 1)
    private String taxYn;

    @Column(name = "ZTAX_TY", length = 20)
    private String taxType;

    @Column(name = "ZTAX_PC", precision = 5, scale = 2)
    private BigDecimal taxPercent;

    @Column(name = "ZROUND_TY", nullable = false, length = 20)
    private String roundType;

    @Column(name = "ZBACK_YN", nullable = false, length = 1)
    private String backPayYn;

    @Column(name = "ZINSR_YN", nullable = false, length = 1)
    private String insuranceYn;

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn;

    @Column(name = "ZNOTE", length = 500)
    private String note;
    
    public void update(PayItemRequestDto dto) {
        this.itemName = dto.getItemName();
        this.itemType = dto.getItemType();
        this.taxYn = dto.getTaxYn();
        this.taxType = dto.getTaxType();
        this.taxPercent = dto.getTaxPercent();
        this.roundType = dto.getRoundType();
        this.backPayYn = dto.getBackPayYn();
        this.insuranceYn = dto.getInsuranceYn();
        this.useYn = dto.getUseYn();
        this.note = dto.getNote();
    }

}
