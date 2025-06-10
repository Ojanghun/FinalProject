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
@Table(name = "PAY_INFO")
public class Pay_Info {

    @Id
    @Column(name = "PAY_IDX", nullable = false, unique = true)
    private int payIdx; // 결재 인덱스
    
    @Column(name = "USER_ID", nullable = false, length = 50) 
    private String id; // 회원 아이디
    
    @Column(name = "PLAN_IDX", nullable = false) 
    private int planIdx; // 플랜 인덱스
    
    @Column(name = "PLAN_STD", nullable = false, insertable = false, updatable = false)
    private LocalDateTime planStd; // 플랜 시작일 → 자동으로 결재된 시간이 들어옴
    
    @Column(name = "PLAN_ED", nullable = false)
    private LocalDateTime planEd; // 플랜 마감일 → 지금은 결재일로 부터 6개월
    
    @Column(name = "PLAN_ACT", nullable = false)
    private int planAct; // 플랜 활성화 상태(0: 활성화 안 됨, 1: 활성화 됨)
    
    @Column(name = "RF_ACT", nullable = false)
    private int rfAct; // 환불 가능 상태(0: 환불 불가, 1: 환불 가능)
}