package com.miniprj.interviewcode.model.payload;

import java.util.List;

public class JwtAuthenticationResponse {

	private Long userId;
	private String noTelp;
	private String email;
	private List<String> roles;
	private String accessToken;
	private String tokenType = "Bearer";

	public JwtAuthenticationResponse(Long userId, String noTelp, String email, List<String> roles, String accessToken) {
		this.userId = userId;
		this.noTelp = noTelp;
		this.email = email;
		this.roles = roles;
		this.accessToken = accessToken;

	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNoTelp() {
		return noTelp;
	}

	public void setNoTelp(String noTelp) {
		this.noTelp = noTelp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
}
