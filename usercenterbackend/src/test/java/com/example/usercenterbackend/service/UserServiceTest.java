package com.example.usercenterbackend.service;

import com.example.usercenterbackend.model.domain.User;
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
        // TODO 还是要把字段封装一下，不然加一个字段好多地方都要改，封装成param的话就可以只改一个地方
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        String planetCode = "12345";
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        userAccount = "yu";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        userAccount = "yupi";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        userAccount = "yu pi";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        userAccount = "dogyupi";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);

        userAccount = "yupi";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertTrue(result > 0);
    }
}