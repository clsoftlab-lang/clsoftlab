package com.example.clsoftlab.entity;

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
@Table(name = "ZHR_EVAL_ITEM_MST")
public class EvalItemMaster extends BaseEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_MST_ID")
    private Long id;

    @Column(name = "TEMPLATE_ID", nullable = false)
    private String templateId;

    @Column(name = "ITEM_NM", nullable = false)
    private String itemName;

    @Column(name = "ITEM_DESC")
    private String itemDesc;

    @Column(name = "ZWEIGHT", nullable = false)
    private Integer weight;

    @Column(name = "ZUSE_YN", nullable = false)
    private String useYn = "Y";

    @Column(name = "ZORDER", nullable = false)
    private Integer order;
    
    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;
}
