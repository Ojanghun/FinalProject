package com.smhrd.repository;

import com.smhrd.entity.Plan_Info;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanInfoRepository extends JpaRepository<Plan_Info, Integer> {

    // 단일 플랜 (자유형 / 계획형) 검색
    Plan_Info findByLiIdxAndPlanType(int liIdx, boolean planType);

    // 전체 플랜 (자유형 + 계획형) 리스트로 조회할 경우 필요
    List<Plan_Info> findByLiIdx(int liIdx);
}
