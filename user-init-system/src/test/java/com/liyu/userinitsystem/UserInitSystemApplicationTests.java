package com.liyu.userinitsystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
class UserInitSystemApplicationTests
{

    @Test
    //测试加密算法
    void testDigest() throws NoSuchAlgorithmException
    {
        String pass = DigestUtils.md5DigestAsHex(("SALT" + "passwrd").getBytes());
        System.out.println(pass);
    }


    @Test
    void contextLoads()
    {
    }

}
