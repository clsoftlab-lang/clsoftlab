package com.example.clsoftlab.entity;

import java.math.BigDecimal;

import com.example.clsoftlab.dto.pay.TaxTableRequestDto;
import com.example.clsoftlab.entity.id.TaxTableId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
@Table(name = "ZHR_TAX_TABLE")
public class TaxTable extends BaseEntity {

	@EmbeddedId
    private TaxTableId id;

    @Column(name = "ZTAX_PC", precision = 5, scale = 2)
    private BigDecimal taxPercent;

    @Column(name = "ZLOCAL_PC", precision = 5, scale = 2)
    private BigDecimal localPercent;

    @Column(name = "ZTOTAL_TAX", precision = 18, scale = 0)
    private BigDecimal totalTax;

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn;

    @Column(name = "ZNOTE", length = 500)
    private String note;
    
    public TaxTable(TaxTableRequestDto dto) {
        TaxTableId newId = new TaxTableId();
        newId.setYear(dto.getYear());
        newId.setFamilyCount(dto.getFamilyCount());
        newId.setIncomeAmount(dto.getIncomeAmount());
        this.id = newId;

        this.taxPercent = dto.getTaxPercent();
        this.localPercent = dto.getLocalPercent();
        this.totalTax = dto.getTotalTax();
        this.useYn = dto.getUseYn();
        this.note = dto.getNote();
    }
    
    public void update(TaxTableRequestDto dto) {
        this.taxPercent = dto.getTaxPercent();
        this.localPercent = dto.getLocalPercent();
        this.totalTax = dto.getTotalTax();
        this.useYn = dto.getUseYn();
        this.note = dto.getNote();
    }
}
