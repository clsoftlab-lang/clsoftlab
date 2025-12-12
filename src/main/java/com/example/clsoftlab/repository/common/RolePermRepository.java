package com.example.clsoftlab.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.RolePerm;
import com.example.clsoftlab.entity.SysMenu;

@Repository
public interface RolePermRepository extends JpaRepository<RolePerm, Long> {

	// roleId로 권한 목록 조회
	@Query("SELECT rp "
			+ "FROM RolePerm rp "
			+ "JOIN FETCH rp.menu m "
			+ "WHERE rp.role.id = :roleId "
			+ "AND m.menuUrl IS NOT NULL "
			+ "ORDER BY m.order ASC")          
    List<RolePerm> findPermsByRoleId(@Param("roleId") String roleId);
	
	
	// roleId로 메뉴 목록 조회
	@Query("SELECT m "
			+ "FROM RolePerm rp "
			+ "JOIN rp.menu m "
			+ "WHERE rp.role.id = :roleId "
			+ "AND rp.read = 'Y' "
			+ "ORDER BY m.order ASC")
	List<SysMenu> findMenusByRoleId(@Param("roleId") String roleId);
}
