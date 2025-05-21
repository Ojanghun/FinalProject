package com.smhrd.entity; // 실제 패키지 경로로 수정

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @Column(length = 50) // ID 길이 적절히 조절
    private String id;

    @Column(nullable = false)
    private String pw;

    @Column(length = 100) // 이름/닉네임 길이
    private String name;

    @Column(length = 20)
    private String phone;

    // 필요에 따라 다른 필드 추가 (email, role 등)
}