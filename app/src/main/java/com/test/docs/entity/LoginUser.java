package com.test.docs.entity;

import lombok.Data;

@Data
public class LoginUser {
    private String loginId;
    private String loginPassword;

    public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
}
