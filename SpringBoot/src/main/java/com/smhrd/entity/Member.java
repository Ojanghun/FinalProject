package com.smhrd.entity;

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
    @Column(length = 50)
    private String id;

    @Column(nullable = false)
    private String pw;

    @Column(length = 100)
    private String name; // 카카오 닉네임이 저장될 필드

    @Column(length = 20)
    private String phone;
}