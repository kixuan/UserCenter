package com.example.usercenterbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.usercenterbackend.model.domain.User;
import com.example.usercenterbackend.model.domain.request.UserLoginRequest;
import com.example.usercenterbackend.model.domain.request.UserRegisterRequest;
import com.example.usercenterbackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.usercenterbackend.constant.UserConstant.ADMIN_ROLE;
import static com.example.usercenterbackend.constant.UserConstant.USER_LOGIN_STATE;

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

    @GetMapping("/current")
    public User getcurrentUsers(HttpServletRequest request) {
        Object object = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) object;
        if (currentUser == null) {
            return null;
        }
        // 啊这里为什么要先get id又get-user啊
        Long userId = currentUser.getId();
        // TODO 检验用户是否合法
        User user = userService.getById(userId);
        return userService.getSafetyUser(user);
    }


    @GetMapping("/search")
    public List<User> searchUsers(String username, HttpServletRequest request) {
        if (isAdmin(request)) {
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("userAccount", username);
        }
        List<User> userList = userService.list(queryWrapper);
        // 我焯这段代码是在干嘛
        return userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
    }

    @GetMapping("/delete")
    // 为什么这里又要加上@RequestBody呢？
    public boolean deleteUser(@RequestBody Long id, HttpServletRequest request) {
        if (isAdmin(request)) {
            return false;
        }
        if (id <= 0) {
            return false;
        }
        return userService.removeById(id);
    }


    /**
     * 仅管理员可查询
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user == null || user.getUserRole() != ADMIN_ROLE;
    }
}
