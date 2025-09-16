package com.example.clsoftlab.repository.pay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.PayItem;

@Repository
public interface PayItemRepository extends JpaRepository<PayItem, String> {

	
	// 검색어로 조회
	@Query("SELECT p "
			+ "FROM PayItem p "
			+ "WHERE (itemName like concat('%',:itemName,'%')) "
			+ "AND (itemType like concat('%', :itemType, '%')) "
			+ "AND (useYn like concat('%', :useYn, '%')) ")
	public Page<PayItem> search( @Param("itemName") String itemName,
            @Param("itemType") String itemType,
            @Param("useYn") String useYn,
            Pageable pageable);
}
