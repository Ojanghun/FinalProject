package com.smhrd.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_INFO") // USER_INFO 테이블과 매핑
public class Member {

    @Id
    @Column(name = "USER_ID", nullable = false, unique = true, length = 50) 
    private String id; // 회원 아이디

    @Column(name = "USER_PW", nullable = false, length = 255)
    private String pw; // 회원 비밀번호 → 암호화 되서 들어감

    @Column(name = "USER_NAME", nullable = false, length = 50)
    private String name; // 카카오 닉네임이 저장될 필드

    @Column(name = "USER_PRO", nullable = true, length = 255)
    private String userpro; // 카카오 프로필이 저장될 필드
    
    @Column(name = "USER_PHONE", nullable = false, length = 50)
    private String phone; // 회원 전화번호
    
    @Column(name = "USER_POINT", nullable = false)
    private int userpoint = 0; // 기본값 0 설정 (코드 레벨)
    
    @Column(name = "JOINED_AT", nullable = false, insertable = false, updatable = false)
    private LocalDateTime joinedat; // 회원가입 날짜 → 자동으로 날짜 입력됨
    
}