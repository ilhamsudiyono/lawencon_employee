package com.miniprj.interviewcode.model.auth;

import java.sql.Timestamp;
import java.time.Instant;

import javax.validation.constraints.*;


public class SignUpRequest {

    @NotBlank
    @Size(min = 3, max = 15)
    private String noTelp;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
    
    @NotBlank
	private Boolean isLogin;
    
    @NotBlank
	private Timestamp createdAt;
    
	
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
    
	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



	public Boolean getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Boolean isLogin) {
		this.isLogin = isLogin;
	}


	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	
    
    
}
