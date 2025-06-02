package com.smhrd.repository;

import com.smhrd.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Optional<Member> findById(String id); // JpaRepository에 이미 포함
// boolean existsById(String id); // JpaRepository에 이미 포함

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    // 필요한 경우 커스텀 쿼리 메소드 추가
}