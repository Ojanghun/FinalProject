package com.smhrd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smhrd.entity.Atd_Log;

public interface AtdLogRepository extends JpaRepository<Atd_Log, Integer>{
	Long countByUserId(String userId);
}
