package com.example.usercenterbackend.controller;

import com.example.usercenterbackend.model.domain.User;
import com.example.usercenterbackend.model.domain.request.UserLoginRequest;
import com.example.usercenterbackend.model.domain.request.UserRegisterRequest;
import com.example.usercenterbackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口
 *
 * @Author:kixuan
 */
@RestController // 这个类里面所有的请求的接口返回值，响应的数据类型都是application json
@RequestMapping("/user")//定义请求的路径
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    // @RequestBody接收前端传递给后端的json字符串中的数据
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassword);
    }


    @PostMapping("/login")
    // @RequestBody接收前端传递给后端的json字符串中的数据
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        return userService.userLogin(userAccount, userPassword, request);
    }
}