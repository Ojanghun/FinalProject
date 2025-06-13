package com.smhrd.service;

import com.smhrd.entity.Member;
import com.smhrd.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private MemberRepository memberRepository;

    // 모든 회원 정보를 조회하는 메소드
    public List<Member> getAllMembers() {
        // 리포지토리의 findAll() 메소드를 호출하여 모든 사용자 정보를 리스트로 받아옵니다.
        return memberRepository.findAll();
    }
}