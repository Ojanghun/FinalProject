package com.smhrd.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pay_info")
public class Pay_Info {

    @Id
    @Column(name = "PAY_IDX", nullable = false, unique = true)
    private int payIdx; // 결제 인덱스

    @Column(name = "USER_ID", nullable = false, length = 50)
    private String id; // 회원 아이디

    @Column(name = "PLAN_IDX", nullable = false)
    private int planIdx; // 플랜 인덱스

    @Column(name = "PLAN_STD", nullable = false, insertable = false, updatable = false)
    private LocalDateTime planStd; // 플랜 시작일

    @Column(name = "PLAN_ED", nullable = false)
    private LocalDateTime planEd; // 플랜 마감일

    @Column(name = "PLAN_ACT", nullable = false)
    private int planAct; // 플랜 활성화 상태

    @Column(name = "RF_ACT", nullable = false)
    private int rfAct; // 환불 가능 상태

    @Column(name = "RF_CP", nullable = false)
    private int rfCp; // 환불 여부(0: 환불 안됨, 1: 환불 됨)
    
    @Column(name = "RJ_AT", nullable = true)
    private LocalDateTime rjAt; // 환불 거부 일자
    
    // 🔗 플랜 이름을 불러오기 위한 연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_IDX", insertable = false, updatable = false)
    private Plan_Info plan;
}

