package com.example.clsoftlab.repository.pay;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.ClubItem;

@Repository
public interface ClubItemRepository extends JpaRepository<ClubItem, Long>, JpaSpecificationExecutor<ClubItem> {

	
	// code 중복 검사
	public boolean existsByClubCode (String clubCode);

	
	//  검색용 list 조회
	public List<ClubItem> findAllByOrderByClubName ();
	
	// clubCode로 항목 조회
	public Optional<ClubItem> findByClubCode (String clubCode);
}
