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
@Table(name = "ADMIN")
public class Admin {

    @Id
    @Column(name = "ADMIN_IDX", nullable = false, unique = true) 
    private int adminIdx; // 관리자 인덱스

    @Column(name = "ADMIN_ID", nullable = false, length = 50) 
    private String adminId; // 관리자 아이디
     
    @Column(name = "ADMIN_PW", nullable = false, length = 255)
    private String adminPw; // 관리자 비밀번호
    
    @Column(name = "ADMIN_AT", nullable = false, insertable = false, updatable = false)
    private LocalDateTime adminAt; // 회원가입 날짜 → 자동으로 날짜 입력됨
    
}