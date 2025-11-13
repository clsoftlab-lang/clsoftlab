package com.example.clsoftlab.repository.hr;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.clsoftlab.entity.BizPlace;

public interface BizPlaceRepository extends JpaRepository<BizPlace, Long>, JpaSpecificationExecutor<BizPlace> {


	// useYn = Y인 리스트 조회
	public List<BizPlace> findAllByUseYnOrderByBizName(String useYn);

	// 중복 검사
	public boolean existsByBizCode(String bizCode);
	
	// 검색용 bizPalce 리스트 조회
	public List<BizPlace> findAllByOrderByBizName ();

	// bizCode로 조회
	public Optional<BizPlace> findByBizCode(String bizCode);
	
}
