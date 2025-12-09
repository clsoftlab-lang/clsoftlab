package com.example.clsoftlab.repository.hr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.EvalItemMaster;

@Repository
public interface EvalItemMasterRepository extends JpaRepository<EvalItemMaster, Long> {

}
