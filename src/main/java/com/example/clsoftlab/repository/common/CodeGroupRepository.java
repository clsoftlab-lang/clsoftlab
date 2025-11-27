package com.example.clsoftlab.repository.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clsoftlab.entity.CodeGroup;

@Repository
public interface CodeGroupRepository extends JpaRepository<CodeGroup, String> {

}
