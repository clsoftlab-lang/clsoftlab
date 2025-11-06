package com.example.clsoftlab.repository.pay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.ClubItem;
import com.example.clsoftlab.entity.ClubPayDetail;
import com.example.clsoftlab.entity.EmployeeMaster;

@Repository
public interface ClubPayDetailRepository extends JpaRepository<ClubPayDetail, Long>, JpaSpecificationExecutor<ClubPayDetail> {

	// 중복 검사
	public boolean existsByEmployee_PernrAndPayYmAndClubItem_Id (String empNo, String payYm, Long clubId);
	
	// 검색용 사원 리스트 조회
	@Query("SELECT DISTINCT e "
			+ "FROM ClubPayDetail c "
			+ "JOIN c.employee e "
			+ "ORDER BY e.name")
	public List<EmployeeMaster> getEmployeeList();
	
	// 검색용 clubItem 리스트 조회
	@Query("SELECT DISTINCT ci "
			+ "FROM ClubPayDetail c "
			+ "JOIN c.clubItem ci "
			+ "ORDER By ci.clubName")
	public List<ClubItem> getClubItemList();
	
}
