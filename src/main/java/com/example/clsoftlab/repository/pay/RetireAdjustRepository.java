package com.example.clsoftlab.repository.pay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.RetireAdjust;
import com.example.clsoftlab.entity.RetireSummary;

@Repository
public interface RetireAdjustRepository extends JpaRepository<RetireAdjust, Long>, JpaSpecificationExecutor<RetireAdjust> {

	// 검색용 항목 list 조회
	@Query ("SELECT DISTINCT r.retireSummary "
			+ "FROM RetireAdjust r ")
	public List<RetireSummary> getRetireSummaryList ();
}
