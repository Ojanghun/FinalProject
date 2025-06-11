package com.smhrd.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smhrd.entity.Ex_Info;

public interface ExInfoRepository extends JpaRepository<Ex_Info, Integer> {
    

	List<Ex_Info> findByExStdAfterOrderByExStdAsc(LocalDateTime now);
	
	// ex_info 테이블에서 년도, 회차 중복 없이 가져옴
	@Query("SELECT DISTINCT YEAR(e.exStd) AS year, e.exRound AS round, exId " +
		       "FROM Ex_Info e WHERE e.exPbexist = 1 ORDER BY YEAR(e.exStd), e.exRound")
		List<Object[]> findAllYearsAndRounds();

}