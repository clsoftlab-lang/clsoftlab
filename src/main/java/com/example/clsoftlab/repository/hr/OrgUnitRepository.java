package com.example.clsoftlab.repository.hr;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.OrgUnit;

@Repository
public interface OrgUnitRepository extends JpaRepository<OrgUnit, Long>, JpaSpecificationExecutor<OrgUnit> {

	// 검색용 조직명 리스트 조회
	@Query("SELECT DISTINCT o.orgName"
			+ "FROM OrgUnit o "
			+ "ORDER BY o.orgName")
	List<String> getOrgNameList ();

	
	// orgCode로 항목 조회
	public Optional<OrgUnit> findByOrgCode(String parentOrgUnitCode);
	
}
