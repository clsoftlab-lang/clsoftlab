package com.example.clsoftlab.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
	
}
