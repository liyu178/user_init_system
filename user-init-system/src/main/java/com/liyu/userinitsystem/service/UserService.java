package com.liyu.userinitsystem.service;

import com.liyu.userinitsystem.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 用户校验码
     * @return 用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);


    /**
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @return 脱敏后的User对象
     */
    //HttpServletRequest request包含客户点发送给服务器的所有信息
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户信息脱敏
     * @param original 原始未脱敏的User对象
     * @return safeUser 脱敏后的的safeUser对象
     */
    User getSafeUser(User original);

    /**
     * 用户退出登录
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);
}
