package com.smhrd.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smhrd.entity.Ex_Info;

public interface ExInfoRepository extends JpaRepository<Ex_Info, Integer> {
    
	List<Ex_Info> findByExStdAfterOrderByExStdAsc(LocalDateTime now);

}