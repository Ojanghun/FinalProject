package com.smhrd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.Exam;

@Repository
public interface TestRepository extends JpaRepository<Exam, Integer>{

}
