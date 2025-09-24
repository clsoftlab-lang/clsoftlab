package com.example.clsoftlab.repository.pay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.ClubItem;

@Repository
public interface ClubItemRepository extends JpaRepository<ClubItem, String> {

	// 검색어로 전체 목록 조회
	public Page<ClubItem> findByClubCodeContainingAndClubNameContainingAndUseYnContaining (String clubCode, String clubName, String useYn, Pageable pageable);
	
	// code 중복 검사
	public boolean existsByClubCode (String clubCode);
}
