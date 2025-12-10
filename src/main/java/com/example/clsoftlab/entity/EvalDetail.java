package com.example.clsoftlab.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.example.clsoftlab.dto.hr.EvalDetailRequestDto;

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
@Table(name = "ZHR_EVAL_DETAIL")
public class EvalDetail extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DETAIL_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESULT_ID", nullable = false)
    private EvalResult evalResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_MST_ID", nullable = false)
    private EvalItemMaster evalItem;

    @Column(name = "ITEM_SCORE", nullable = false)
    private Integer itemScore = 0;

    @Column(name = "ITEM_COMMENT", length = 1000)
    private String itemComment;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;
    
//    public void update(EvaluationDetailRequestDto dto) {
//    	this.itemScore = dto.getPoint();
//    	this.itemComment = dto.getComment();
//    }
}
