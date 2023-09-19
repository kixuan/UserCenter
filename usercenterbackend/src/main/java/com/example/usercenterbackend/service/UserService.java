package com.example.usercenterbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.usercenterbackend.model.domain.User;
import com.example.usercenterbackend.model.domain.UserLoginParam;
import com.example.usercenterbackend.model.domain.UserRegisterParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 醒酒器
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2023-09-04 13:45:58
 */
public interface UserService extends IService<User> {


    /**
     * 用户注册
     * @return 新用户 id
     */
    Long userRegister(UserRegisterParam userRegisterParam);

    /**
     * 用户登录
     */
    // request为了拿到session，记录用户的登录状态
    User userLogin(UserLoginParam userLoginParam);

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户登出
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);
}
