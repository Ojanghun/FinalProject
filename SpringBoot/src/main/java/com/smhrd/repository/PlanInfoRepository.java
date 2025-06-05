package com.smhrd.repository;

import com.smhrd.entity.Plan_Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanInfoRepository extends JpaRepository<Plan_Info, Integer> {

    // liIdx와 planType으로 단일 플랜 정보 가져오기
    Plan_Info findByLiIdxAndPlanType(int liIdx, boolean planType);

}
