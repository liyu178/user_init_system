package com.liyu.userinitsystem.service;
import java.util.Date;

import com.liyu.userinitsystem.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserServiceTest
{
    @Resource
    private UserService userService;

    @Test
    public void testSave()
    {
        User user = new User();
        user.setUserAccount("liyu");
        user.setUserPassword("1234");
        user.setPhone("phone3");
        user.setUserName("jlkj");
        user.setEmail("email444");
        user.setUserRole(0);
        boolean result = userService.save(user);
        System.out.println(user.getId());
        System.out.println(user.getUserAccount());
        System.out.println(result);
        Assertions.assertTrue(result);


    }

    @Test
    void userRegister()
    {
        String userAccount = "liyu123456";
        String userPassword = "123456789";
        String checkPassword = "123456789";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        System.out.println(result);//调用方法后返回的是插入数据的id

    }
}