package com.smhrd.repository; // 실제 패키지 경로로 수정

import com.smhrd.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    // 로그인 시 ID로 회원 정보 조회 (Spring Security는 UserDetailsService 사용 권장)
    // 여기서는 간단히 ID로만 찾는 예시 (pw 비교는 서비스에서 또는 Security가 알아서 함)
    Optional<Member> findById(String id);

    // Spring Security의 UserDetailsService를 구현하는 경우 아래와 같은 메소드가 유용
    // Member findByUsername(String username); // 여기서 username이 Member의 id 필드와 매핑된다고 가정

    // ID 중복 체크용 (ID 존재 여부만 확인)
    boolean existsById(String id);
}