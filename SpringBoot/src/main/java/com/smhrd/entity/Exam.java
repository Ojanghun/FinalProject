package com.smhrd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Exam {
	
	@Id
	@Column(name = "PB_ID", unique = true, nullable = false)
	private int pbId; // 문제 아이디 - PK

	@Column(name = "EX_ID", nullable = false)
	private int exId; // 시험 아이디 - 출제 년도 별 묶음

	@Column(name = "PB_NUM", nullable = false)
	private int pbNum; // 문제 번호 - 출제 년도 별 문제 번호

	@Column(name = "PB_QUES", length = 1000, nullable = false)
	private String pbQues; // 문제 질문

	@Column(name = "PB_DETAIL")
	private byte[] pbDetail; // 문제 상세설명

	@Column(name = "PB_CHOI1", length = 255, nullable = false)
	private String pbChoi1; // 문제 선지1

	@Column(name = "PB_CHOI2", length = 255, nullable = false)
	private String pbChoi2; // 문제 선지2

	@Column(name = "PB_CHOI3", length = 255, nullable = false)
	private String pbChoi3; // 문제 선지3

	@Column(name = "PB_CHOI4", length = 255, nullable = false)
	private String pbChoi4; // 문제 선지4

	@Column(name = "PB_ANS", nullable = false)
	private int pbAns; // 정답 번호

	@Column(name = "PB_SOLU", length = 1000, nullable = false)
	private String pbSolu; // 해설

	@Column(name = "PB_CR", nullable = false)
	private String pbCr; // 문제 정답률

	@Column(name = "PB_TOPIC", length = 255, nullable = false)
	private int pbTopic; // 주제 - 문제 유형 0~56



}
