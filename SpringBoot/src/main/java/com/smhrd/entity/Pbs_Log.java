package com.smhrd.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PBS_LOG")
public class Pbs_Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 이 줄을 꼭 추가
    @Column(name = "PBS_IDX", nullable = false, unique = true) 
    private int pbsIdx; // 문제 풀이 로그 인덱스

    @Column(name = "USER_ID", nullable = false, length = 50) 
    private String userId; // 회원 아이디
    
    @Column(name = "PB_IDX", nullable = false)
	private int pbIdx; // 문제 인덱스
    
    @Column(name = "PBS_CHECK", nullable = false)
    private int pbsCheck; // 문제 정답 여부(0: 틀림, 1: 맞음)
    
    @Column(name = "PBS_AT", nullable = false)
    private LocalDateTime pbsAt; // 문제 푼 날짜 → 지정해줘야함
    
    @Column(name = "PB_NUM", nullable = false)
	private int pbNum; // 문제 번호
    
}