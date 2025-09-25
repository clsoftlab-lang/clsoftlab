package com.example.clsoftlab.entity;

import com.example.clsoftlab.dto.pay.CalcOrderRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
@Table(name = "ZHR_CALC_ORDER")
public class CalcOrder extends BaseEntity {

	@Id
    @Column(name = "ZITEM_CD", length = 50)
    private String itemCode;

    @Column(name = "ZORDER", nullable = false)
    private Integer order;

    @Column(name = "ZGROUP_CD", nullable = false, length = 20)
    private String groupCode;

    @Column(name = "ZNOTE", length = 500)
    private String note;

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZDEPENDS_ON") // DB의 ZDEPENDS_ON 컬럼을 통해 Join
    private CalcOrder dependsOn;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "ZITEM_CD")
    private PayItem payItem;
    
    public void update (CalcOrderRequestDto dto) {
    	this.order = dto.getOrder();
    	this.groupCode = dto.getGroupCode();
    	this.note = dto.getNote();
    	this.useYn = dto.getUseYn();
    }
}
