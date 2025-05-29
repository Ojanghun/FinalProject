package com.smhrd.service;

import com.smhrd.entity.Member;
import com.smhrd.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Member join(Member member) {
        if (memberRepository.existsById(member.getId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다: " + member.getId());
        }
        member.setPw(passwordEncoder.encode(member.getPw()));
        return memberRepository.save(member);
    }

    public boolean isIdExists(String id) {
        return memberRepository.existsById(id);
    }
}