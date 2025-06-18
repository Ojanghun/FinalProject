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
@Table(name = "USER_SCORE")
public class User_Score {

    @Id
    @Column(name = "SC_IDX", nullable = false, unique = true) 
    private int scIdx; // 점수 인덱스
    
    @Column(name = "USER_ID", nullable = false, length = 50) 
    private String userId; // 회원 아이디
    
    @Column(name = "LI_IDX", nullable = false)
    private int liIdx; // 자격증 인덱스
    
    @Column(name = "EX_CAT", nullable = false, length = 50)
    private String exCat;
    
    @Column(name = "SC_CHAP1", nullable = true) 
    private int scChap1; // 1과목 점수
    
    @Column(name = "SC_CHAP2", nullable = true) 
    private int scChap2; // 2과목 점수
    
    @Column(name = "SC_CHAP3", nullable = true) 
    private int scChap3; // 3과목 점수
    
    @Column(name = "SC_CHAP4", nullable = true) 
    private int scChap4; // 4과목 점수
    
    @Column(name = "SC_CHAP5", nullable = true) 
    private int scChap5; // 5과목 점수
    
    @Column(name = "SC_CHAP6", nullable = true) 
    private int scChap6; // 6과목 점수
    
    @Column(name = "SC_AT", nullable = false)
    private LocalDateTime scAt; // 문제 풀이 날짜 → 데이터가 입력됨
    
}