package com.smhrd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Member {
	
	@Id
	private String id;

	private String pw;
	
	@Column(length = 3000)
	private String nick;
	
	private String phone;
	
	private String addr;
	
}
