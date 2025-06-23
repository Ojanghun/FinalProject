package com.smhrd.repository;

import com.smhrd.entity.Member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// Optional<Member> findById(String id); // JpaRepository에 이미 포함
// boolean existsById(String id); // JpaRepository에 이미 포함

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    // 필요한 경우 커스텀 쿼리 메소드 추가
	
	// 필수형 플랜인 사용자의 이름과 휴대폰 번호 가져오기
    @Query(value = """
            SELECT u.user_name, u.user_phone
            FROM user_info u
            JOIN pay_info p ON u.user_id = p.user_id
            JOIN plan_info pi ON p.plan_idx = pi.plan_idx
            WHERE pi.plan_type = true and p.plan_act = 1
            """, nativeQuery = true)
        List<Object[]> findUserNameAndPhonesWithTruePlanType();
	
}