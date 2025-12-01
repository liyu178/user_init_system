package com.liyu.userinitsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//从mybatis_plus文档中粘贴过来，启动类时自动扫描mapper中的内容，将增删改查注入到项目中
//即一行代码不写就能实现怎删改查的操作
@MapperScan("com.liyu.userinitsystem.mapper")
public class UserInitSystemApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(UserInitSystemApplication.class, args);
    }

}
