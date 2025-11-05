package com.example.clsoftlab.entity;

import com.example.clsoftlab.dto.pay.ClubItemRequestDto;

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
@Table(name = "ZHR_CLUB_ITEM")
public class ClubItem extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ZCLUB_CD", nullable = false, length = 10, unique = true)
    private String clubCode;
    
    @Column(name = "ZCLUB_NM", nullable = false, length = 100)
    private String clubName;

    @Column(name = "ZAMOUNT", nullable = false)
    private Long amount;

    @Column(name = "ZUSE_YN", nullable = false, length = 1)
    private String useYn;

    @Column(name = "ZNOTE", length = 500)
    private String note;
    
    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;
    
    public void update (ClubItemRequestDto dto) {
    	this.clubName = dto.getClubName();
    	this.amount = dto.getAmount();
    	this.useYn = dto.getUseYn();
    	this.note = dto.getNote();
    }
}
