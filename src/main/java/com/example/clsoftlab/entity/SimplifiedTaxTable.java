package com.example.clsoftlab.entity;

import java.math.BigDecimal;

import com.example.clsoftlab.dto.pay.SimplifiedTaxTableRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "ZHR_SIMPLIFIED_TAX_TABLE")
public class SimplifiedTaxTable extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ZTAX_ID")
    private Long taxId;

    @Column(name = "ZYEAR", nullable = false, length = 4)
    private String year;

    @Column(name = "ZFAMILY_CNT", nullable = false)
    private Integer familyCount;

    @Column(name = "ZINCOME_AMT", nullable = false)
    private Long incomeAmt;

    @Column(name = "ZTAX_PC", precision = 5, scale = 2)
    private BigDecimal taxPc;

    @Column(name = "ZLOCAL_PC", precision = 5, scale = 2)
    private BigDecimal localPc;

    @Column(name = "ZTOTAL_TAX")
    private Long totalTax;

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn;

    @Column(name = "ZNOTE", length = 500)
    private String note;
    
    @Version
    @Column(name = "ZVERSION", nullable = false)
    private Integer version;
    
    public void update(SimplifiedTaxTableRequestDto dto) {
    	this.taxPc = dto.getTaxPc();
    	this.localPc = dto.getLocalPc();
    	this.totalTax = dto.getTotalTax();
    	this.useYn = dto.getUseYn();
    	this.note = dto.getNote();
    }
}
