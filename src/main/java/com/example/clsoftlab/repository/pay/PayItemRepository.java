package com.example.clsoftlab.repository.pay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.PayItem;

@Repository
public interface PayItemRepository extends JpaRepository<PayItem, String>, JpaSpecificationExecutor<PayItem>  {

}