package com.example.clsoftlab.entity;

import com.example.clsoftlab.dto.pay.ClubPayDetailRequestDto;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ZHR_CLUB_PAY_DETAIl")
public class ClubPayDetail extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZEMP_NO", referencedColumnName = "PERNR", nullable = false)
    private EmployeeMaster employee;

    @Column(name = "ZPAY_YM", nullable = false, length = 6)
    private String payYm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ZCLUB_CD", referencedColumnName = "ZCLUB_CD", nullable = false)
    private ClubItem clubItem;

    @Column(name = "ZAMOUNT", nullable = false)
    private Long amount;
    
    @Column(name = "ZNOTE", length = 500)
    private String note;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;
    
    public void update (ClubPayDetailRequestDto dto) {
    	this.amount = dto.getAmount();
    	this.note = dto.getNote();
    }
}
