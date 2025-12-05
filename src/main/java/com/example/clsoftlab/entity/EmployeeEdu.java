package com.example.clsoftlab.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@DynamicInsert
@DynamicUpdate
@Table(name = "ZHR_EMP_EDU")
public class EmployeeEdu extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EDU_ID")
    private Long id; // PK

    @Column(name = "PERNR", nullable = false, length = 10)
    private String pernr; // 사번 (FK)

    @Column(name = "ZSEQ", nullable = false)
    private Integer seq; // 순번

    @Column(name = "ZEDU_TYPE", nullable = false, length = 20)
    private String eduType;

    @Column(name = "ZEDU_NAME", nullable = false, length = 100)
    private String eduName; // 과정명

    @Column(name = "ZEDU_ORG", nullable = false, length = 100)
    private String eduOrg; // 교육기관

    @Column(name = "ZSTART_DATE", nullable = false)
    private LocalDate startDate; // 시작일

    @Column(name = "ZEND_DATE", nullable = false)
    private LocalDate endDate; // 종료일

    @ColumnDefault("'N'")
    @Column(name = "ZCOMPLETE_YN", nullable = false, length = 1)
    private String completeYn; // 수료 여부

    @ColumnDefault("'N'")
    @Column(name = "ZMANDATORY_YN", nullable = false, length = 1)
    private String mandatoryYn;

    @Column(name = "ZEDU_SCORE")
    private Integer eduScore;

    @Column(name = "ZHOUR")
    private Integer hour; // 총 교육시간
    
    @ColumnDefault("0")
    @Column(name = "ZCOST", precision = 13, scale = 0)
    private BigDecimal cost;

    @Column(name = "ZATTACH_ID", length = 100)
    private String attachId; // 첨부파일 ID

    @Column(name = "ZREMARK", length = 200)
    private String remark; // 비고
    
    @Version
    @Column(name = "VERSION")
    private Long version;
    
//    public void update (EmployeeEduRequestDto dto) {
//    	this.seq = dto.getSeq();
//    	this.eduName = dto.getEduName();
//    	this.eduType = dto.getEduType();
//    	this.eduOrg = dto.getEduOrg();
//    	this.startDate = dto.getStartDate();
//    	this.endDate = dto.getEndDate();
//    	this.completeYn = dto.getCompleteYn();
//    	this.hour = dto.getHour();
//    	this.cost = dto.getCost();
//    	this.attachId = dto.getAttachId();
//    	this.remark = dto.getRemark();
//    }
}
