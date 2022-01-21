/**
 * -------------------------------------------------------------------------------------------------
 * File    :  RegisterRequest.java
 * Author  : niukaijun <kaijun@bmxlabs.com>
 * Purpose :
 * Created : 28 Oct 2018 by niukaijun <kaijun@bmxlabs.com>
 * -------------------------------------------------------------------------------------------------
 * <p>
 * Copyright (C) 2018-2019   MaxIM.Top
 * <p>
 * You may obtain a copy of the licence at http://www.maxim.top/LICENCE-MAXIM.md
 * <p>
 * -------------------------------------------------------------------------------------------------
 */

package net.xmmpp.uc.common.dto.response;

public class UserRegistrationResponse {

    private Long userId;

    private String username;

    private String password;

    private String mobile;

    private String email;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserRegistrationResponse{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
