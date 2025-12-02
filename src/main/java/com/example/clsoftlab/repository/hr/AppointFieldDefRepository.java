package com.example.clsoftlab.repository.hr;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.AppointFieldDef;

@Repository
public interface AppointFieldDefRepository extends JpaRepository<AppointFieldDef, Long> {

	
	// ruleTypes로 list 조회
	public List<AppointFieldDef> findByRuleTypeIn (Set<String> ruleTypes);
}
