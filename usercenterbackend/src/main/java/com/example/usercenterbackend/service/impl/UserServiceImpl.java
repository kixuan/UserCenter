package com.example.usercenterbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usercenterbackend.Mapper.UserMapper;
import com.example.usercenterbackend.common.ErrorCode;
import com.example.usercenterbackend.exception.BusinessException;
import com.example.usercenterbackend.model.domain.User;
import com.example.usercenterbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.usercenterbackend.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author 醒酒器
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-09-04 13:45:58
 */
@Slf4j
@Service
// @Component
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    private final String SALT = "kixuan";

    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        // 1.校验用户的账户、密码、校验密码，是否符合要求
        // 1.1.非空校验
        // 1.2. 账户长度不小于4位
        // 1.3. 密码就不小于8位
        // 1.4  星球账号不能大于5位
        if ((StringUtils.isAnyBlank(userAccount, userPassword, checkPassword))
                || (userAccount.length() < 4)
                || (userPassword.length() < 8)
                || (planetCode.length() > 5)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }

        // 1.5 账户不包含特殊字符
        String validRule = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%…… &*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validRule).matcher(userAccount);
        // 如果包含非法字符，则返回
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        // 1.6 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        // 1.7 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.DUP_INFO);
        }
        // 1.8 星球账号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode", planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.DUP_INFO);
        }
        // 2.对密码进行加密（密码千万不要直接以明文存储到数据库中）
        String verifyPassword = DigestUtils.md5DigestAsHex((SALT + userPassword)
                .getBytes(StandardCharsets.UTF_8));
        // 3. 向数据库插入用户数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(verifyPassword);
        user.setPlanetCode(planetCode);
        int res = userMapper.insert(user);
        if (res < 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        return user.getId();
    }

    /**
     * 用户登录
     *
     * @param userAccount
     * @param userPassword
     * @param request
     * @return
     */
    @Override
    // TODO 改成只有一个param
    // request为了拿到session，记录用户的登录状态
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1.校验
        if ((StringUtils.isAnyBlank(userAccount, userPassword))
                || (userAccount.length() < 4)
                || (userPassword.length() < 8)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }

        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        // 2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword)
                .getBytes(StandardCharsets.UTF_8));
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount Cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        // 3.用户脱敏
        User safetyUser = getSafetyUser(user);

        // 4.记录用户的登录状态
        // 拿到session，再用setAttribute往session里面放一些值
        // map类型，存储键值对，给用户的登录状态分配一个键
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return user;
    }

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        User safetyUser = new User();
        // password和isDelete不返回给前端
        safetyUser.setId(originUser.getId());
        safetyUser.setUserName(originUser.getUserName());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUpdateTime(originUser.getUpdateTime());
        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        // 直接把session里面的值给删掉就算登出了
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
}




