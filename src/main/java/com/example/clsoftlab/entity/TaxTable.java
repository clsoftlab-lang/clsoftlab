package com.example.clsoftlab.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.clsoftlab.dto.pay.TaxTableRequestDto;
import com.example.clsoftlab.entity.id.TaxTableId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
public class TaxTable {

	@EmbeddedId
    private TaxTableId id;

    @Column(name = "ZTAX_PC", precision = 5, scale = 2)
    private BigDecimal taxPerCent;

    @Column(name = "ZLOCAL_PC", precision = 5, scale = 2)
    private BigDecimal localPercent;

    @Column(name = "ZTOTAL_TAX", precision = 18, scale = 0)
    private BigDecimal totalTax;

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn;

    @Column(name = "ZNOTE", length = 500)
    private String note;

    // --- 감사 컬럼 (생략하지 않음) ---
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "CREATED_BY", nullable = false, updatable = false, length = 50)
    private String createdBy;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "UPDATED_BY", length = 50)
    private String updatedBy;
    

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        // 실제 프로젝트에서는 SecurityContextHolder를 통해 현재 로그인한 사용자 ID를 할당합니다.
        if (this.createdBy == null) {
        	this.createdBy = "system"; 
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
        // 실제 프로젝트에서는 SecurityContextHolder를 통해 현재 로그인한 사용자 ID를 할당합니다.
        if (this.updatedBy == null) {
        	this.updatedBy = "system";
        }
    }
    
    public TaxTable(TaxTableRequestDto dto) {
        TaxTableId newId = new TaxTableId();
        newId.setYear(dto.getYear());
        newId.setFamilyCount(dto.getFamilyCount());
        newId.setIncomeAmount(dto.getIncomeAmount());
        this.id = newId;

        this.taxPerCent = dto.getTaxPercent();
        this.localPercent = dto.getLocalPercent();
        this.totalTax = dto.getTotalTax();
        this.useYn = dto.getUseYn();
        this.note = dto.getNote();
    }
    
    public void update(TaxTableRequestDto dto) {
        this.taxPerCent = dto.getTaxPercent();
        this.localPercent = dto.getLocalPercent();
        this.totalTax = dto.getTotalTax();
        this.useYn = dto.getUseYn();
        this.note = dto.getNote();
    }
}
