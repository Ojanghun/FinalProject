package com.smhrd.repository;

import com.smhrd.entity.Pay_Info;
import com.smhrd.projection.PayWithLicenseDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface PayInfoRepository extends JpaRepository<Pay_Info, Integer> {

	// ✅ 유저 ID + 자격증 liIdx로 활성화된 결제 정보 찾기 (기존)
	@Query("SELECT p FROM Pay_Info p WHERE p.id = :userId AND p.planAct = 1 AND p.planIdx IN (SELECT pi.planIdx FROM Plan_Info pi WHERE pi.liIdx = :liIdx)")
	List<Pay_Info> findByUserIdAndLiIdx(@Param("userId") String userId, @Param("liIdx") int liIdx);

	// ✅ 유저 ID 기준 전체 결제 이력 조회 (최신순)
	List<Pay_Info> findByIdOrderByPlanStdDesc(String id);

	// (또는 JPQL로 구현하고 싶다면 아래 방식도 가능)
	// @Query("SELECT p FROM Pay_Info p WHERE p.id = :userId ORDER BY p.planStd
	// DESC")
	// List<Pay_Info> findAllByUserId(@Param("userId") String userId);

	// ✅ 사용자 ID 기준 결제 이력 + liName을 projection으로 가져오기
	// ✅ PayInfoRepository.java
	@Query("SELECT new com.smhrd.projection.PayWithLicenseDTOImpl(p.planIdx, li.liName, p.planStd, p.planEd, pi.planType, p.planAct) " +
		       "FROM Pay_Info p " +
		       "JOIN Plan_Info pi ON p.planIdx = pi.planIdx " +
		       "JOIN Li_Info li ON pi.liIdx = li.liIdx " +
		       "WHERE p.id = :userId " +
		       "ORDER BY p.planStd DESC")
		List<PayWithLicenseDTO> findDetailedPaymentsByUserId(@Param("userId") String userId);


	
	@Modifying
	@Transactional
	@Query("UPDATE Pay_Info p SET p.planAct = 0 WHERE p.planEd < CURRENT_TIMESTAMP AND p.planAct = 1")
	void deactivateExpiredPlans();


	@Modifying
	@Transactional
	@Query(value = "UPDATE pay_info " +
	               "SET rf_act = CASE " +
	               "    WHEN NOW() BETWEEN plan_ed AND DATE_ADD(plan_ed, INTERVAL 7 DAY) THEN 1 " +
	               "    ELSE 0 " +
	               "END", nativeQuery = true)
	void updateRefundStatus();

	// 자유형 이용자 수 (plan_type = false)
	@Query("SELECT COUNT(p) FROM Pay_Info p " +
	       "JOIN Plan_Info pi ON p.planIdx = pi.planIdx " +
	       "WHERE pi.liIdx = :liIdx AND pi.planType = false AND p.planAct = 1")
	int countActiveUsersForFreePlan(@Param("liIdx") int liIdx);

	// 계획형 이용자 수 (plan_type = true)
	@Query("SELECT COUNT(p) FROM Pay_Info p " +
	       "JOIN Plan_Info pi ON p.planIdx = pi.planIdx " +
	       "WHERE pi.liIdx = :liIdx AND pi.planType = true AND p.planAct = 1")
	int countActiveUsersForStrictPlan(@Param("liIdx") int liIdx);

	@Query("SELECT p FROM Pay_Info p " +
		   "JOIN Plan_Info pi ON p.planIdx = pi.planIdx " +
		   "WHERE p.id = :userId AND pi.planIdx = :planIdx AND p.rfAct = 1")
	Pay_Info findTop1RefundableByUserIdAndLiIdx(@Param("userId") String userId, @Param("planIdx") int planIdx);
	
}
