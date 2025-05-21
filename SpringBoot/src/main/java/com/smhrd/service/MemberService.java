package com.smhrd.service; // 실제 패키지 경로로 수정

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
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        // 비밀번호 암호화
        member.setPw(passwordEncoder.encode(member.getPw()));
        return memberRepository.save(member);
    }

    // ID 중복 체크 서비스 메소드
    public boolean isIdExists(String id) {
        return memberRepository.existsById(id);
    }

    // 로그인 검증은 Spring Security가 PasswordEncoder와 UserDetailsService를 통해 처리합니다.
    // 여기에 별도의 login(Member vo) 메소드는 Spring Security 사용 시 일반적으로 필요하지 않거나,
    // 다른 목적으로 (예: 사용자 정보 조회 후 세션에 추가 정보 저장) 사용될 수 있습니다.
    // 아래는 예시로 남겨두지만, Spring Security 흐름에서는 직접 호출되지 않을 수 있습니다.
    public Member findMemberById(String id) {
        return memberRepository.findById(id).orElse(null);
    }
}