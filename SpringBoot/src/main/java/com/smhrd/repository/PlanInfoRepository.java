package com.smhrd.repository;

import com.smhrd.entity.Plan_Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PlanInfoRepository extends JpaRepository<Plan_Info, Integer> {

    // 단일 플랜 (자유형 / 계획형) 검색
    Plan_Info findByLiIdxAndPlanType(int liIdx, boolean planType);

    // 전체 플랜 (자유형 + 계획형) 리스트로 조회할 경우 필요
    List<Plan_Info> findByLiIdx(int liIdx);
    
    @Query(value = """
    	    SELECT li.li_name AS licenseName,
    	           CASE pi.plan_type
    	               WHEN 0 THEN '탐구형'
    	               WHEN 1 THEN '필수형'
    	           END AS planName,
    	           COUNT(p.user_id) AS userCount
    	    FROM plan_info pi
    	    JOIN li_info li ON li.li_idx = pi.li_idx
    	    LEFT JOIN pay_info p ON pi.plan_idx = p.plan_idx
    	    GROUP BY li.li_name, pi.plan_type
    	""", nativeQuery = true)
    	List<Map<String, Object>> findAllPlanUsageWithLicense();


}
