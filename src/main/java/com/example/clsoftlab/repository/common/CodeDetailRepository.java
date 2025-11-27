package com.example.clsoftlab.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.CodeDetail;

@Repository
public interface CodeDetailRepository extends JpaRepository<CodeDetail, Long> {

	// 해당 groupId의 useYn이 Y인 리스트 조회
	public List<CodeDetail> findAllByGroupIdAndUseYnOrderByOrder (String groupId, String useYn);
}
