package com.example.clsoftlab.repository.hr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.OrgUnit;

@Repository
public interface OrgUnitRepository extends JpaRepository<OrgUnit, String>, JpaSpecificationExecutor<OrgUnit> {
	

}
