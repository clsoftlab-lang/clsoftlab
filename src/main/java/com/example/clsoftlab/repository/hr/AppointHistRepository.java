package com.example.clsoftlab.repository.hr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.AppointHist;

@Repository
public interface AppointHistRepository extends JpaRepository<AppointHist, String> {

	// 사번으로 리스트 조회
	public List<AppointHist> findAllByEmployee_PernrAndIsCanceledOrderByEffDateDesc (String pernr, String isCanceled);
}
