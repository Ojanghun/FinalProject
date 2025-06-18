package com.smhrd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "CHATBOT_INFO")
public class Chatbot_Info {
	
	@Id
	@Column(name = "CB_IDX", unique = true, nullable = false)
	private int cbIdx; // 챗봇 인덱스 - PK

	@Column(name = "pb_idx", nullable = false) 
    private int pbIdx; // 문제 인덱스
	
	@Column(name = "CB_QUES", length = 2000, nullable = false)
	private String cbQues; // 챗봇 질문

	@Column(name = "CB_ANS", length = 2000, nullable = false)
	private String cbAns; // 챗봇 해설
	
}
