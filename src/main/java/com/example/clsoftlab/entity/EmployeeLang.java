package com.example.clsoftlab.entity;

import java.time.LocalDate;

import com.example.clsoftlab.dto.hr.EmployeeLangRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "ZHR_EMP_LANG")
public class EmployeeLang extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LANG_ID")
    private Long id;

    @Column(name = "PERNR", nullable = false, length = 10)
    private String pernr;

    @Column(name = "ZSEQ", nullable = false)
    private Integer seq;

    @Column(name = "ZLANG", nullable = false, length = 20)
    private String lang;

    @Column(name = "ZEXAM_NAME", nullable = false, length = 50)
    private String examName;

    @Column(name = "ZSCORE", nullable = false, length = 20)
    private String score;

    @Column(name = "ZGET_DATE", nullable = false)
    private LocalDate getDate;

    @Column(name = "ZEXP_DATE")
    private LocalDate expDate;

    @Column(name = "ZSPEAK_LVL", length = 10)
    private String speakLvl;

    @Column(name = "ZREAD_LVL", length = 10)
    private String readLvl;

    @Column(name = "ZATTACH_ID", length = 100)
    private String attachId;

    @Column(name = "ZREMARK", length = 200)
    private String remark;
    
    public void update (EmployeeLangRequestDto dto) {
    	this.seq = dto.getSeq();
    	this.lang = dto.getLang();
    	this.examName = dto.getExamName();
    	this.score = dto.getScore();
    	this.getDate = dto.getGetDate();
    	this.expDate = dto.getExpDate();
    	this.speakLvl = dto.getSpeakLvl();
    	this.readLvl = dto.getReadLvl();
    	this.attachId = dto.getAttachId();
    	this.remark = dto.getRemark();
    }
}
