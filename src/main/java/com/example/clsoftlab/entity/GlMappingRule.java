package com.example.clsoftlab.entity;

import com.example.clsoftlab.dto.pay.GlMappingRuleRequestDto;

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
@Table(name = "ZHR_GL_MAPPING_RULE")
public class GlMappingRule extends BaseEntity {

 	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MAPPING_ID")
    private Long id;

    @Column(name = "ZITEM_CD", nullable = false, length = 50)
    private String itemCode;

    @Column(name = "ZBIZ_CD", nullable = false, length = 10)
    private String bizCode;

    @Column(name = "ZCOST_CNTR", nullable = false, length = 20)
    private String costCntr;

    @Column(name = "ZGL_ACCT", nullable = false, length = 30)
    private String glAccount;

    @Column(name = "ZNOTE", length = 500)
    private String note;

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn; 
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZITEM_CD", insertable = false, updatable = false)
    private PayItem payItem;

    
    public void update (GlMappingRuleRequestDto dto) {
    	this.glAccount = dto.getGlAccount();
    	this.useYn = dto.getUseYn();
    	this.note = dto.getNote();
    }
}
