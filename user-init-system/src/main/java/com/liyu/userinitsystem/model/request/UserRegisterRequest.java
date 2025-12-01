package com.liyu.userinitsystem.model.request;

import lombok.Data;

//该request包中的类封装前端需要传入的参数
@Data
public class UserRegisterRequest
{
    /**
     * 用户注册请求参数
     * 账户、密码、校验码
     */
    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
