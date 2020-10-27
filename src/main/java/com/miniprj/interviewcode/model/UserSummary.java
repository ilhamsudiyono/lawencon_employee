package com.miniprj.interviewcode.model;

public class UserSummary {
    
	
	private Long id;
    private String noTelp;
    private String email;
    
    
	public UserSummary(Long id, String noTelp, String email) {
		super();
		this.id = id;
		this.noTelp = noTelp;
		this.email = email;
	}
	
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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


}
