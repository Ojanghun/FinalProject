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
@Table(name = "TOPIC_INFO")
public class Topic_Info {

    @Id
    @Column(name = "TOPIC_IDX", nullable = false, unique = true) 
    private int topicIdx; // 문제 주제 인덱스
    
    @Column(name = "LI_IDX", nullable = false) 
    private int liIdx; // 자격증 인덱스
    
    @Column(name = "TOPIC_NUM", nullable = false) 
    private int topicNum; // 자격증 내 주제 번호
    
    @Column(name = "TOPIC_NAME", nullable = false, length = 255)
    private String topicName; // 주제 이름
    
}