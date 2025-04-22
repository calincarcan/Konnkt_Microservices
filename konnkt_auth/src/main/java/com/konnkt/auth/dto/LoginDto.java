package com.konnkt.auth.dto;

import org.springframework.stereotype.Component;

@Component
public class LoginDto {
    private String loginInfo;
    private String password;

    public String getLoginInfo() {
        return loginInfo;
    }

    public LoginDto setLoginInfo(String loginInfo) {
        this.loginInfo = loginInfo;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
