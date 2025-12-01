package com.liyu.userinitsystem.model.request;

import lombok.Data;

@Data
public class UserLoginRequest
{
    /**
     * 用户登录请求参数
     */
    private String userAccount;
    private String userPassword;
}
