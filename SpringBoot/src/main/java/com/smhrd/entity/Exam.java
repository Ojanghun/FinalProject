package com.smhrd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "PB_INFO")
public class Exam {
	
	@Id
	@Column(name = "PB_IDX", unique = true, nullable = false)
	private int pbId; // 문제 아이디 - PK

	@Column(name = "LI_IDX", nullable = false) 
    private int liIdx; // 자격증 인덱스
	
	@Column(name = "EX_IDX", nullable = false) 
    private int exId; // 시험 인덱스
	
	@Column(name = "PB_NUM", nullable = false)
	private int pbNum; // 문제 번호 - 출제 년도 별 문제 번호

	@Column(name = "PB_QUES", length = 1000, nullable = false)
	private String pbQues; // 문제 질문

	@Column(name = "PB_DETAIL", length = 1000)
	private String pbDetail; // 문제 상세설명 → 왜 문자열이 아니지?

	@Column(name = "PB_CHOI1", length = 1000, nullable = false)
	private String pbChoi1; // 문제 선지1

	@Column(name = "PB_CHOI2", length = 1000, nullable = false)
	private String pbChoi2; // 문제 선지2

	@Column(name = "PB_CHOI3", length = 1000, nullable = false)
	private String pbChoi3; // 문제 선지3

	@Column(name = "PB_CHOI4", length = 1000, nullable = false)
	private String pbChoi4; // 문제 선지4

	@Column(name = "PB_ANS", nullable = false)
	private int pbAns; // 정답 번호

	@Column(name = "PB_SOLU", length = 2000, nullable = false)
	private String pbSolu; // 해설

	@Column(name = "PB_CR", length = 255, nullable = false)
	private String pbCr; // 문제 정답률

	@Column(name = "TOPIC_IDX", nullable = false) 
    private int pbTopic;
	
}
