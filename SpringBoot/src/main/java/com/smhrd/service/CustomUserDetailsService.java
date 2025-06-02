package com.smhrd.service; // 실제 패키지 경로로 수정

import com.smhrd.entity.Member;
import com.smhrd.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User; // Spring Security의 User 클래스
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 여기서 username은 로그인 시 입력된 아이디(id)를 의미합니다.
        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("아이디에 해당하는 사용자를 찾을 수 없습니다: " + username));

        // 사용자의 권한 설정 (여기서는 모든 사용자에게 "ROLE_USER" 부여)
        // 실제로는 Member 엔티티에 role 필드를 두고 동적으로 권한을 부여하는 것이 좋습니다.
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        // Spring Security의 User 객체를 반환 (아이디, DB에 저장된 암호화된 비밀번호, 권한 목록)
        return new User(member.getId(), member.getPw(), authorities);
    }
}