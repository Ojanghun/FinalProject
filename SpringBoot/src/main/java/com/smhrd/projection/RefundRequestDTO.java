// com.smhrd.projection.RefundRequestDTO.java
package com.smhrd.projection;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // 기본 생성자
public class RefundRequestDTO {

	private int rfIdx; // 환불 인덱스
	private String userId; // 사용자 ID
	private String rfVpath; // 인증 영상 경로
	private String rfName; // 예금주 이름
	private String rfBank; // 은행명
	private String rfAccnum; // 계좌번호
	private LocalDateTime rfAt; // 환불 요청 일시
	private String liName; // 자격증 이름
	private String planType; // 플랜 종류 (필수형 / 탐구형)
	private int planPrice; // 플랜 가격
	private int rfCp; // 0: 요청중, 1: 완료
	private LocalDateTime apAt; // 환급 승인 일시
	private int payIdx;
	private LocalDateTime rjAt; // 거절일시 추가

	public RefundRequestDTO(int rfIdx, String userId, String rfVpath, String rfName, String rfBank, String rfAccnum,
			LocalDateTime rfAt, String liName, String planType, int planPrice, int rfCp, LocalDateTime apAt, int payIdx,
			LocalDateTime rjAt) {
		this.rfIdx = rfIdx;
		this.userId = userId;
		this.rfVpath = rfVpath;
		this.rfName = rfName;
		this.rfBank = rfBank;
		this.rfAccnum = rfAccnum;
		this.rfAt = rfAt;
		this.liName = liName;
		this.planType = planType;
		this.planPrice = planPrice;
		this.rfCp = rfCp;
		this.apAt = apAt;
		this.payIdx = payIdx;
		this.rjAt = rjAt;
	}
}
