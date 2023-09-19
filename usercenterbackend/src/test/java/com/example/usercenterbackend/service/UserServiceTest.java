package com.example.usercenterbackend.service;

import com.example.usercenterbackend.model.domain.User;
import com.example.usercenterbackend.model.domain.UserRegisterParam;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUserName("xxz");
        user.setUserAccount("132");
        user.setAvatarUrl("baidu.com");
        user.setGender(0);
        user.setUserPassword("123");
        user.setPhone("123123");
        user.setEmail("123");

        boolean save = userService.save(user);
        // 增加断言，ctrl+p可以查看需要的参数
        Assertions.assertEquals(true, save);
        System.out.println(user.getId());
    }

    @Test
    void userRegister() {
        // 还是要把字段封装一下，不然加一个字段好多地方都要改，封装成param的话就可以只改一个地方
        UserRegisterParam userRegisterParam = new UserRegisterParam();
        userRegisterParam.setUserAccount("yupi");
        userRegisterParam.setUserPassword("123456");
        userRegisterParam.setCheckPassword("123456");
        userRegisterParam.setPlanetCode("1234");

        long result = userService.userRegister(userRegisterParam);
        Assertions.assertEquals(-1, result);

        userRegisterParam.setUserAccount("yu");
        result = userService.userRegister(userRegisterParam);
        Assertions.assertEquals(-1, result);

        userRegisterParam.setUserAccount("yupi");
        userRegisterParam.setUserPassword("123456");
        result = userService.userRegister(userRegisterParam);
        Assertions.assertEquals(-1, result);

        userRegisterParam.setUserAccount("yu pi");
        userRegisterParam.setUserPassword("12345678");
        result = userService.userRegister(userRegisterParam);
        Assertions.assertEquals(-1, result);

        userRegisterParam.setCheckPassword("123456789");
        result = userService.userRegister(userRegisterParam);
        Assertions.assertEquals(-1, result);

        userRegisterParam.setUserAccount("dogyupi");
        userRegisterParam.setCheckPassword("12345678");
        result = userService.userRegister(userRegisterParam);
        Assertions.assertEquals(-1, result);

        userRegisterParam.setUserAccount("yupi");
        result = userService.userRegister(userRegisterParam);
        Assertions.assertTrue(result > 0);
    }
}