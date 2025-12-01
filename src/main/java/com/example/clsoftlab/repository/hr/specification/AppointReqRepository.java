package com.example.clsoftlab.repository.hr.specification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.AppointReq;

@Repository
public interface AppointReqRepository extends JpaRepository<AppointReq, String> {

}
