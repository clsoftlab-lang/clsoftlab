package com.example.clsoftlab.repository.pay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.SimplifiedTaxTable;

@Repository
public interface SimplifiedTaxTableRepository extends JpaRepository<SimplifiedTaxTable, Long>, JpaSpecificationExecutor<SimplifiedTaxTable> {

	// 중복 검사
	public boolean existsByYearAndFamilyCountAndIncomeAmt (String year, Integer familyCount, Long incomeAmt);
}
