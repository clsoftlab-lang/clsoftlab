package com.example.clsoftlab.entity;

import com.example.clsoftlab.dto.pay.PayCycleRequestDto;

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
@Table(name = "ZHR_PAY_CYCLE")
public class PayCycle extends BaseEntity {

	@Id
    @Column(name = "ZJOB_GRP", nullable = false, length = 20)
    private String jobGroup; // 직군 코드 (PK)

    @Column(name = "ZSTART_DAY", nullable = false)
    private int startDay; // 급여 기준 시작일

    @Column(name = "ZEND_DAY", nullable = false)
    private int endDay; // 급여 기준 종료일

    @Column(name = "ZPAY_DAY", nullable = false)
    private int payDay; // 실제 급여 지급일

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn; // 사용 여부 (Y/N)

    @Column(name = "ZNOTE", length = 500)
    private String note; // 비고 및 설명
    
    public void update (PayCycleRequestDto dto) {
    	this.startDay = dto.getStartDay();
    	this.endDay = dto.getEndDay();
    	this.payDay = dto.getPayDay();
    	this.useYn = dto.getUseYn();
    	this.note = dto.getNote();
    }
}
