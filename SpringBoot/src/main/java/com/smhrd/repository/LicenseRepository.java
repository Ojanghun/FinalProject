package com.smhrd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smhrd.entity.Atd_Log;


@Repository
public interface LicenseRepository extends JpaRepository<Atd_Log, Integer> {
	
    // 특정 사용자 ID가 같은 날짜(yyyy-MM-dd)에 출석한 기록이 있는지 확인
	// CURRENT_DATE : 오늘 날짜(시간은 00:00:00 반환)
	@Query("SELECT COUNT(a) FROM Atd_Log a WHERE a.userId = :userId AND FUNCTION('DATE', a.atdAt) = CURRENT_DATE")
	int checkUserAttendanceToday(@Param("userId") String userId);

	List<Atd_Log> findByUserId(String id);
	
	
	// 최근 3회 주제별 틀린 횟수 정리
	// userid값으로 묶어서, 최근날짜로 정렬 후 300개의 데이터를 가져와 pbs_check가 0인 pb_idx 값 가져오기
	// pb_idx 값으로 pb_info에서 topic_idx를 찾고 topic_idx로 GroupBy 후 count 세고 count가 높은 순으로 정렬
	@Query(value = """
		    SELECT COUNT(p.topic_idx) AS topic_count, p.topic_idx
		    FROM pb_info p
		    WHERE p.pb_idx IN (
		        SELECT pb_idx
		        FROM (
		            SELECT pl.pb_idx, ROW_NUMBER() OVER (PARTITION BY pl.user_id ORDER BY pl.pbs_at DESC) AS rn, pl.pbs_check
		            FROM pbs_log pl
		            WHERE pl.user_id = :userId
		        ) pbs_log
		        WHERE rn <= 300 AND pbs_check = 0
		    )
		    GROUP BY p.topic_idx
		    ORDER BY topic_count DESC
		""", nativeQuery = true)
		List<Object[]> getWrongRateRecent3(@Param("userId") String userId);

   
}
