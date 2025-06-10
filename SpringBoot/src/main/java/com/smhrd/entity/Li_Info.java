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
    
    @Column(name = "LI_CPC", nullable = false)
    private int liCpc; // 자격증 과목 개수
    
	@Column(name = "LI_CHAP1", length = 255)
	private String liChap1; // 자격증 1번째 과목 이름
	
	@Column(name = "LI_CHAP2", length = 255)
	private String liChap2; // 자격증 2번째 과목 이름
	
	@Column(name = "LI_CHAP3", length = 255)
	private String liChap3; // 자격증 3번째 과목 이름
	
	@Column(name = "LI_CHAP4", length = 255)
	private String liChap4; // 자격증 4번째 과목 이름
	
	@Column(name = "LI_CHAP5", length = 255)
	private String liChap5; // 자격증 5번째 과목 이름
	
	@Column(name = "LI_CHAP6", length = 255)
	private String liChap6; // 자격증 6번째 과목 이름
    
}