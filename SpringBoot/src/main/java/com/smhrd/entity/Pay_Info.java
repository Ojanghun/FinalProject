package com.smhrd.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "PAY_INFO")
public class Pay_Info {

    @Id
    @Column(name = "PAY_IDX", nullable = false, unique = true)
    private int payIdx; // 결재 인덱스
    
    @Column(name = "USER_ID", nullable = false, length = 50) 
    private String id;
    
    @Column(name = "PLAN_IDX", nullable = false) 
    private int planIdx;
    
    @Column(name = "PLAN_STD", nullable = false, insertable = false, updatable = false)
    private LocalDateTime planStd; // 플랜 시작일 → 자동으로 결재된 시간이 들어와야함
    
    @Column(name = "PLAN_ED", nullable = false)
    private LocalDateTime planEd;
    
    
}