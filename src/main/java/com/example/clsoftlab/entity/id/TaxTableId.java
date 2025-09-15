package com.example.clsoftlab.entity.id;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TaxTableId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "ZYEAR")
	private Integer year;
	
	@Column(name = "ZFAMILY_CNT")
	private Integer familyCount;
	
	@Column(name = "ZINCOME_AMT")
	private BigDecimal incomeAmount;

}
