package com.example.usercenterbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.usercenterbackend.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 醒酒器
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2023-09-04 13:45:58
 */
public interface UserService extends IService<User> {


    /**
     * 用户注册
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    Long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

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
