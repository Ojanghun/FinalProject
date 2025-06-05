package com.smhrd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.Atd_Log;


@Repository
public interface LicenseRepository extends JpaRepository<Atd_Log, Integer> {
	
    // 특정 사용자 ID가 같은 날짜(yyyy-MM-dd)에 출석한 기록이 있는지 확인
    @Query("SELECT COUNT(a) FROM Atd_Log a WHERE a.id = :id AND FUNCTION('DATE', a.atdAt) = CURRENT_DATE")
    int countTodayLogById(@Param("id") String id);
}
