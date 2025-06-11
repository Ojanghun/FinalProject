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
@Table(name = "EX_INFO")
public class Ex_Info {

    @Id
    @Column(name = "EX_IDX", nullable = false, unique = true) 
    private int exId; // 시험 인덱스
     
    @Column(name = "LI_IDX", nullable = false) 
    private int liIdx; // 자격증 인덱스
    
    @Column(name = "APP_STD", nullable = false)
    private LocalDateTime appStd; // 원서 접수 시작일
    
    @Column(name = "APP_ED", nullable = false)
    private LocalDateTime appEd; // 원서 접수 마감일
    
    @Column(name = "EX_STD", nullable = false)
    private LocalDateTime exStd; // 시험 시작일
    
    @Column(name = "EX_ED", nullable = false)
    private LocalDateTime exEd; // 시험 종료일
    
    @Column(name = "EX_ROUND", nullable = false)
    private int exRound; // 해당 연도 시험 회차
    
    @Column(name = "EX_RD", nullable = false)
    private LocalDateTime exRd; // 합격자 발표일
    
    @Column(name = "EX_PBEXIST", nullable = false) 
    private int exPbexist; // 시험 문제 존재 여부
    
}