package com.example.clsoftlab.repository.pay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.TaxTable;
import com.example.clsoftlab.entity.id.TaxTableId;

@Repository
public interface TaxTableRepository extends JpaRepository<TaxTable, TaxTableId>, JpaSpecificationExecutor<TaxTable> {

		// 세율/구간 중복 검색
		@Query("SELECT Count(t) "
				+ "FROM TaxTable t "
				+ "WHERE (t.id.year = :year) "
				+ "AND (t.id.familyCount = :familyCount) "
				+ "AND (t.id.incomeAmount = :incomeAmount) ")
		public long countOverlappingTaxTable ( @Param("year") int year,
	            @Param("familyCount") long familyCount,
	            @Param("incomeAmount") long incomeAmount);
}
