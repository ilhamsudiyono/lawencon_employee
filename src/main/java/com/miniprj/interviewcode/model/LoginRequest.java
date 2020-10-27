package com.miniprj.interviewcode.model;

import javax.validation.constraints.NotBlank;


public class LoginRequest {
    @NotBlank
    private String noTelpOrEmail;

    @NotBlank
    private String password;

    public String getNoTelpOrEmail() {
        return noTelpOrEmail;
    }

    public void setNoTelpOrEmail(String noTelpOrEmail) {
        this.noTelpOrEmail = noTelpOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
