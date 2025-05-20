package com.smhrd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smhrd.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String>{

	Member findByIdAndPw(String id, String pw);
	
}
