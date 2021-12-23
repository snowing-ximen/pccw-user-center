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

package net.pccw.uc.common.dto.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


public class RegistrationRequest {

    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";

    @Length(max = 100)
    @NotEmpty(message = "The username can not be empty.")
    private String username;

    @Length(max = 10)
    @NotEmpty(message = "The password can not be empty.")
    private String password;

    @Length(max = 11)
    @Pattern(regexp = REGEX_MOBILE)
    @NotEmpty(message = "The mobile can not be empty.")
    private String mobile;

    @Email
    @Length(max = 128)
    @NotEmpty(message = "The email can not be empty.")
    private String email;


    public static String getRegexMobile() {
        return REGEX_MOBILE;
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
}
