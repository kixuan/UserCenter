package com.example.usercenterbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.usercenterbackend.common.BaseResponse;
import com.example.usercenterbackend.common.ErrorCode;
import com.example.usercenterbackend.common.ResultUtil;
import com.example.usercenterbackend.exception.BusinessException;
import com.example.usercenterbackend.model.domain.User;
import com.example.usercenterbackend.model.domain.request.UserLoginRequest;
import com.example.usercenterbackend.model.domain.request.UserRegisterRequest;
import com.example.usercenterbackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtil.success(result);
    }


    @PostMapping("/login")
    // @RequestBody接收前端传递给后端的json字符串中的数据
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtil.success(user);
    }

    @GetMapping("/current")
    public BaseResponse<User> getcurrentUsers(HttpServletRequest request) {
        Object object = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) object;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        // 啊这里为什么要先get id又get-user啊
        Long userId = currentUser.getId();
        // TODO 检验用户是否合法
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtil.success(safetyUser);
    }


    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NOT_AUTH);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("userAccount", username);
        }
        List<User> userList = userService.list(queryWrapper);
        // 我焯这段代码是在干嘛
        List<User> searchList = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtil.success(searchList);
    }

    @GetMapping("/delete")
    // 为什么这里又要加上@RequestBody呢？
    public BaseResponse<Boolean> deleteUser(@RequestBody Long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NOT_AUTH);
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        boolean b = userService.removeById(id);
        return ResultUtil.success(b);
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

    //芥末这里加的又是post
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        int result = userService.userLogout(request);
        return ResultUtil.success(result);
    }
}
