package com.example.clsoftlab.entity;

import com.example.clsoftlab.dto.pay.AllowCycleRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name =  "ZHR_ALLOW_CYCLE")
public class AllowCycle extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ZCYCLE_ID")
    private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZITEM_CD", nullable = false, unique = true)
    private PayItem payItem;

    @Column(name = "ZCYCLE", nullable = false, length = 20)
    private String cycle;

    @Column(name = "ZNOTE", length = 500)
    private String note;

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn;
    
    @Version
    @Column(name = "ZVERSION", nullable = false)
    private Integer version;
    
    public void update (AllowCycleRequestDto dto) {
    	this.cycle = dto.getCycle();
    	this.note = dto.getNote();
    	this.useYn = dto.getUseYn();
    }
}
