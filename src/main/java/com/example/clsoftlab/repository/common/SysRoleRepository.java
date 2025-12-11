package com.example.clsoftlab.repository.common;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.SysRole;

@Repository
public interface SysRoleRepository extends JpaRepository<SysRole, String> {

	// list 조회
	public List<SysRole> findAllByOrderByOrder ();
}
