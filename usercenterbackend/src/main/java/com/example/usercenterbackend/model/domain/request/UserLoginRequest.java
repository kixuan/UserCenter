package com.example.usercenterbackend.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author 醒酒器
 */
// 实现 Serializable 就是序列化
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -6615735376118984333L;

    private String userAccount;
    private String userPassword;
}
