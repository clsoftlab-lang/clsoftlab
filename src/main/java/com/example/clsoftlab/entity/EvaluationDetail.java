package com.example.clsoftlab.entity;

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
@Table(name = "ZHR_EVAL_DETAIL")
public class EvaluationDetail extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DETAIL_ID")
    private Long id;

    @Column(name = "PERNR", nullable = false, length = 10)
    private String pernr;

    @Column(name = "ZYEAR", nullable = false, length = 4)
    private String year;

    @Column(name = "ZSEQ", nullable = false, length = 2)
    private String seq;
    
    @Column(name = "ZEV_TYPE", nullable = false, length = 2)
    private String evType;

    @Column(name = "ZITEM_CD", nullable = false, length = 10)
    private String itemCode;

    @Column(name = "ZITEM_NM", nullable = false, length = 100)
    private String itemName;

    @Column(name = "ZPOINT", nullable = false)
    private Integer point;

    @Column(name = "ZCOMMENT", length = 255)
    private String comment;
}
