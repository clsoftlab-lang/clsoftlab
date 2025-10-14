package com.example.clsoftlab.entity;

import com.example.clsoftlab.dto.hr.EvaluationDetailRequestDto;

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
@Table(name = "ZHR_EVAL_DETAIL")
public class EvaluationDetail extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DETAIL_ID")
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "EVAL_RESULT_ID") 
	private EvaluationResult evaluationResult;
    
    @Column(name = "ZITEM_CD", nullable = false, length = 10)
    private String itemCode;

    @Column(name = "ZITEM_NM", nullable = false, length = 100)
    private String itemName;

    @Column(name = "ZPOINT", nullable = false)
    private Integer point;

    @Column(name = "ZCOMMENT", length = 255)
    private String comment;
    
    public void update(EvaluationDetailRequestDto dto) {
    	this.point = dto.getPoint();
    	this.comment = dto.getComment();
    }
}
