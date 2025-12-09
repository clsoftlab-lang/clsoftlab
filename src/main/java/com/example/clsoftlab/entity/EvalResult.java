package com.example.clsoftlab.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@DynamicInsert
@DynamicUpdate
@Table(name = "ZHR_EVAL_RESULT")
public class EvalResult extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESULT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEMPLATE_ID", nullable = false)
    private EvalTemplate template;

    @Column(name = "EVAL_STEP", nullable = false, length = 10)
    private String evalStep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TARGET_EMP_ID", nullable = false)
    private EmployeeMaster targetEmp; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVAL_EMP_ID", nullable = false)
    private EmployeeMaster evalEmp;   

    
    @Column(name = "TOTAL_SCORE")
    private Integer totalScore = 0;

    @Column(name = "EVAL_GRADE", length = 10)
    private String evalGrade;

    @Column(name = "ZCOMMENT", length = 2000)
    private String comment;


    @Column(name = "ZSTATUS", nullable = false, length = 1)
    private String status = "T";

    @Column(name = "SUBMIT_DT")
    private LocalDateTime submitDate;
    
    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;
    
    
    
    // 등급 변경용 (기준은 임의로 선정했음)
    public void updateTotalScoreAndGrade(int totalScore) {
    	this.totalScore = totalScore;
        if (totalScore >= 90) {
            this.evalGrade = "A";
        } else if (totalScore >= 80) {
            this.evalGrade = "B";
        } else if (totalScore >= 70) {
            this.evalGrade = "C";
        } else {
            this.evalGrade = "D";
        }
    }
}
