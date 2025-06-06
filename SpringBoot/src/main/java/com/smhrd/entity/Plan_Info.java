package com.smhrd.entity;

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
@Table(name = "PLAN_INFO")
public class Plan_Info {

    @Id
    @Column(name = "PLAN_IDX", nullable = false, unique = true) 
    private int planIdx; // 플랜 인덱스
    
    @Column(name = "LI_IDX", nullable = false) 
    private int liIdx;
    
    @Column(name = "PLAN_TYPE", nullable = false)
    private Boolean planType; // 플랜 타입(0: 탐구형, 1: 필수형)
    
    @Column(name = "PLAN_PRICE", nullable = false)
    private int planPrice;
    
}