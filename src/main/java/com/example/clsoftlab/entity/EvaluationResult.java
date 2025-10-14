package com.example.clsoftlab.entity;

import com.example.clsoftlab.dto.hr.EvaluationResultRequestDto;

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
@Table(name = "ZHR_EVAL_RESULT")
public class EvaluationResult extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVAL_ID")
    private Long id;

    @Column(name = "PERNR", nullable = false, length = 10)
    private String pernr;

    @Column(name = "ZYEAR", nullable = false, length = 4)
    private String year;

    @Column(name = "ZSEQ", nullable = false, length = 2)
    private String seq;

    @Column(name = "ZEV_TYPE", nullable = false, length = 2)
    private String evType;

    @Column(name = "ZTOTAL_SCORE", nullable = false)
    private Integer totalScore;

    @Column(name = "ZGRADE", nullable = false, length = 2)
    private String grade;

    @Column(name = "ZREVIEWER", nullable = false, length = 10)
    private String reviewer;

    @Column(name = "ZCOMMENT", length = 255)
    private String comment;

    @Version
    @Column(name = "VERSION")
    private Long version;
    
    public void update (EvaluationResultRequestDto dto) {
    	this.year = dto.getYear();
    	this.seq = dto.getSeq();
    	this.evType = dto.getEvType();
    	this.totalScore = dto.getTotalScore();
    	this.grade = dto.getGrade();
    	this.reviewer = dto.getReviewer();
    	this.comment = dto.getComment();
    }
    
    // 등급 변경용 (기준은 임의로 선정했음)
    public void updateTotalScoreAndGrade(int totalScore) {
    	this.totalScore = totalScore;
        if (totalScore >= 90) {
            this.grade = "A";
        } else if (totalScore >= 80) {
            this.grade = "B";
        } else if (totalScore >= 70) {
            this.grade = "C";
        } else {
            this.grade = "D";
        }
    }
}
