package com.example.usercenterbackend.model.domain;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class UserLoginParam {

    private String userAccount;

    private String userPassword;

    private HttpServletRequest request;

}
