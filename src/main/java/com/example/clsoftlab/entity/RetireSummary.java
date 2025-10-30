package com.example.clsoftlab.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.clsoftlab.dto.pay.RetireSummaryRequestDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "ZHR_RETIRE_SUMMARY")
public class RetireSummary extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERNR", referencedColumnName = "PERNR", nullable = false)
    private EmployeeMaster employee;

    @Column(name = "ENTRY_DT", nullable = false)
    private LocalDate entryDate;

    @Column(name = "RETIRE_DT", nullable = false)
    private LocalDate retireDate;

    @Column(name = "ZSERVICE_YRS", nullable = false, precision = 10, scale = 2)
    private BigDecimal serviceYears;

    @Column(name = "ZAVG_SALARY", nullable = false, precision = 18, scale = 2)
    private BigDecimal avgSalary;

    @Column(name = "ZRETIRE_PAY", nullable = false, precision = 18, scale = 2)
    private BigDecimal retirePay;

    @Column(name = "ZTAX_AMOUNT", nullable = false, precision = 18, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "ZFINAL_PAY", nullable = false, precision = 18, scale = 2)
    private BigDecimal finalPay;

    @Column(name = "ZNOTE", length = 1000)
    private String note;
    
    @Version
    @Column(name = "VERSION")
    private Long version;
    
    // 계산용 필드
    @OneToMany(mappedBy = "retireSummary", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RetireAdjust> adjustments = new ArrayList<>();
    
    
    // 퇴지금 재 계산
    public void recalculatePayments() {
        
        // 1. 기본 퇴직금 계산
        BigDecimal baseRetirePay = BigDecimal.ZERO;
        if (this.avgSalary != null && this.serviceYears != null) {
            baseRetirePay = this.avgSalary.multiply(this.serviceYears);
        }

        // 2. 가감 항목 총액 계산
        BigDecimal totalAdjustAmount = BigDecimal.ZERO;
        
        // 'this.adjustments' 리스트(DB에 없던 가상 연결고리)를 순회합니다.
        for (RetireAdjust adjust : this.adjustments) {
            
            BigDecimal calculated = adjust.getCalculatedAmount(this.avgSalary);

            if ("EXTRA".equals(adjust.getAdjType())) {
                totalAdjustAmount = totalAdjustAmount.add(calculated);
            } else if ("DEDUCT".equals(adjust.getAdjType())) {
                totalAdjustAmount = totalAdjustAmount.subtract(calculated);
            }
        }

        // 3. 최종 퇴직금 (가감 후)
        BigDecimal finalRetirePay = baseRetirePay.add(totalAdjustAmount);
        this.setRetirePay(finalRetirePay); // ★ 총 퇴직금 업데이트

        // 4. 세금 재계산 (실제로는 복잡한 세금 계산 로직(TaxService) 호출 필요)
        BigDecimal newTax = finalRetirePay.multiply(new BigDecimal("0.10")); // 임시 세금 수식입니다. [수정 필요]        
        this.setTaxAmount(newTax); // ★ 세금 업데이트

        // 5. 실지급액 계산
        this.setFinalPay(finalRetirePay.subtract(newTax)); // ★ 실지급액 업데이트
    }
    
    public void update (RetireSummaryRequestDto dto) {
    	this.entryDate = dto.getEntryDate();
    	this.retireDate = dto.getRetireDate();
    	this.avgSalary = dto.getAvgSalary();
    	this.note = dto.getNote();
    	this.serviceYears = dto.getServiceYears();
    	
    	this.recalculatePayments();
    }
}
