package com.example.clsoftlab.entity;

import java.math.BigDecimal;

import com.example.clsoftlab.dto.pay.RetireAdjustRequestDto;

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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ZHR_RETIRE_ADJUST")
public class RetireAdjust extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY) // 성능을 위해 LAZY 로딩
	@JoinColumn(name = "ZRETIRE_SUMMARY_ID", nullable = false)
	private RetireSummary retireSummary;

    @NotBlank // NotNull + "" 방지
    @Column(name = "ZADJ_TY", nullable = false, length = 10)
    private String adjType;

    @NotBlank
    @Column(name = "ZADJ_REASON", nullable = false, length = 100)
    private String adjReason;

    @Min(0) // 0 이상의 값만 허용 (양수)
    @Column(name = "ZADJ_AMOUNT", precision = 18, scale = 2)
    private BigDecimal adjAmount;

    @Column(name = "ZADJ_EXPR", length = 500)
    private String adjExpr;

    @Column(name = "ZNOTE", length = 1000)
    private String note;

    @Version
    @Column(name = "VERSION")
    private Integer version;
    
    // 계산 값 부모 테이블로 전달
    public BigDecimal getCalculatedAmount(BigDecimal baseAvgSalary) {
        // 1. 수식(adjExpr)이 우선순위가 높음
        if (this.adjExpr != null && !this.adjExpr.isBlank()) {
            
            // (예시) "ZAVG_SALARY * 0.5" 케이스만 처리
            // [주의] 실제 구현 시에는 보안을 위해 ScriptEngine 등 안전한 수식 파서 필요 [임시용]
            if (this.adjExpr.equals("ZAVG_SALARY * 0.5") && baseAvgSalary != null) {
                return baseAvgSalary.multiply(new BigDecimal("0.5"));
            }
            
            // ... 기타 수식 로직 ...
            return BigDecimal.ZERO; // 수식 해석 실패 시 0원
        }
        
        // 2. 수식이 없으면 고정 금액(adjAmount) 사용
        if (this.adjAmount != null) {
            return this.adjAmount;
        }

        return BigDecimal.ZERO;
    }
    
    public void update (RetireAdjustRequestDto dto) {
    	this.adjType = dto.getAdjType();
    	this.adjReason = dto.getAdjReason();
    	this.adjAmount = dto.getAdjAmount();
    	this.adjExpr = dto.getAdjExpr();
    	this.note = dto.getNote();
    }
}
