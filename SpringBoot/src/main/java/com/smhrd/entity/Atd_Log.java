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
@Table(name = "ATD_LOG")
public class Atd_Log {

    @Id
    @Column(name = "ATD_IDX", nullable = false, unique = true) 
    private int atdIdx; // 출석 인덱스

    @Column(name = "USER_ID", nullable = false, length = 50) 
    private String userId; // 회원 아이디
     
    @Column(name = "ATD_AT", nullable = false, insertable = false, updatable = false)
    private LocalDateTime atdAt; // 출석 날짜 → 자동으로 날짜가 입력됨
    
}