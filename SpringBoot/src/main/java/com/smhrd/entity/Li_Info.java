package com.smhrd.entity;

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
@Table(name = "LI_INFO")
public class Li_Info {

    @Id
    @Column(name = "LI_IDX", nullable = false, unique = true) 
    private int liIdx; // 자격증 인덱스
    
    @Column(name = "LI_NAME", nullable = false, length = 50) 
    private String liName; // 자격증 이름
    
    @Column(name = "LI_TYPE", nullable = false)
    private Boolean liType; // 자격증 타입(필기: 0, 실기: 1)
    
    @Column(name = "LI_TIME", nullable = false) 
    private int liTime; // 자격증 시험시간 인덱스
    
    @Column(name = "LI_PBC", nullable = false)
    private int liPbc; // 자격증 문제 개수
    
}