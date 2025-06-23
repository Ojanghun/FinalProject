package com.smhrd.projection;

public class UserContactDTO {
	private String userName;
	private String userPhone;

	public UserContactDTO(String userName, String userPhone) {
		this.userName = userName;
		this.userPhone = userPhone;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserPhone() {
		return userPhone;
	}
}
