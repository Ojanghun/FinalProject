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
@Table(name = "REFUND_INFO")
public class Refund_Info {

    @Id
    @Column(name = "RF_IDX", nullable = false, unique = true) 
    private int rfIdx; // 환불 인덱스

    @Column(name = "PAY_IDX", nullable = false)
    private int payIdx; // 결재 인덱스

    @Column(name = "USER_ID", nullable = false, length = 50) 
    private String id; // 회원 아이디
   
    @Column(name = "RF_VPATH", nullable = false, length = 255)
    private String rfVpath; // 합격 인증 영상 경로
    
    @Column(name = "RF_NAME", nullable = false, length = 50)
    private String rfName; // 환불 예금주
 
    @Column(name = "RF_BANK", nullable = false, length = 50)
    private String rfBank; // 환불은행
    
    @Column(name = "RF_ACCNUM", nullable = false, length = 50)
    private String rfAccnum; // 환불계좌번호
    
    @Column(name = "RF_AT", nullable = false, insertable = false, updatable = false)
    private LocalDateTime rfAt; // 환불 날짜
    
}