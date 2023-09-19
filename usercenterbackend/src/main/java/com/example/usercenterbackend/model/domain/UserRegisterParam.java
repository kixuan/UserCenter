package com.example.usercenterbackend.model.domain;

import lombok.Data;

@Data
public class UserRegisterParam {

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String planetCode;
}
