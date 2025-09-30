package com.example.clsoftlab.repository.hr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.clsoftlab.entity.BizPlace;

public interface BizPlaceRepository extends JpaRepository<BizPlace, String>, JpaSpecificationExecutor<BizPlace> {


	public List<BizPlace> findAllByUseYnOrderByBizNameAsc(String useYn);

}
