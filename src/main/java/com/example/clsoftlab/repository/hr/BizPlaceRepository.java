package com.example.clsoftlab.repository.hr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.clsoftlab.entity.BizPlace;

public interface BizPlaceRepository extends JpaRepository<BizPlace, String>, JpaSpecificationExecutor<BizPlace> {

}
