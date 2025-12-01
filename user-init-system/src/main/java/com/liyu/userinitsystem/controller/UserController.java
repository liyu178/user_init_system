package com.liyu.userinitsystem.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liyu.userinitsystem.common.BaseResponse;
import com.liyu.userinitsystem.common.ErrorCode;
import com.liyu.userinitsystem.common.ResultUtils;
import com.liyu.userinitsystem.excption.BusinessException;
import com.liyu.userinitsystem.model.constant.UserConstant;
import com.liyu.userinitsystem.model.domain.User;
import com.liyu.userinitsystem.model.request.UserLoginRequest;
import com.liyu.userinitsystem.model.request.UserRegisterRequest;
import com.liyu.userinitsystem.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*  controller中前端调用的方法的参数类型：
    1.参数类型：String userName，不需要@RequestBody
    本质： 前端传递的「业务参数」（比如查询关键词）
    注入方式（Spring 如何处理）：从 URL 查询字符串（?userName=xxx）或表单中提取
    2.参数类型：HttpServletRequest，不需要@RequestBody
    本质： quest「请求上下文对象」（封装了整个 HTTP 请求信息）
    注入方式（Spring 如何处理）：Spring 自动创建并注入，包含请求头、Session、URL、请求体等所有信息
    3.参数类型：UserRegisterRequest（model中封装的实体类），需要@RequestBody
    本质： 前端传递的「复杂业务参数」（多字段 JSON）
    注入方式（Spring 如何处理）：从 HTTP 请求体中解析 JSON 绑定
 */

/*  校验写在哪里？
    • controller 层倾向于对请求参数本身的校验，不涉及业务逻辑本身（越少越好）
    • service 层是对业务逻辑的校验（有可能被 controller 之外的类调用）
 */

@RestController
@RequestMapping("/service")
public class UserController
{
    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param userRegisterRequest model.request.UserRegister包中封装的参数
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest)
    {
        if(userRegisterRequest == null)
        {
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword))
        {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        long l = userService.userRegister(userAccount, userPassword, checkPassword);
        //return ResultUtils.success(l);
        throw new BusinessException(ErrorCode.PARAMS_ERROR);//用自定义抛出异常
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request)
    {
        if(userLoginRequest == null)
        {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword))
        {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    /**
     * 用户退出登录
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request)
    {
        if(request == null)
        {
            return null;
        }
        int i = userService.userLogout(request);
        return ResultUtils.success(i);
    }

    @PostMapping("/search")
    public BaseResponse<List<User>> userSearch(String userName, HttpServletRequest request)
    {
        if(!isAdmin(request))
        {
            //return ResultUtils.error(ErrorCode.NO_AUTHORITY,"没有管理员权限");
            throw new BusinessException(ErrorCode.NO_AUTHORITY,"没有管理员权限");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isAnyBlank(userName))
        {
            queryWrapper.like("userName", userName);
        }
        List<User> userList = userService.list();
        //将查询到的用户信息脱敏后用集合返回,语法为java8知识点
        List<User> collect = userList.stream().map(user -> userService.getSafeUser(user)).collect(Collectors.toList());
        return ResultUtils.success(collect);

    }

    @PostMapping("/delete")
    public BaseResponse<Boolean>  userDelete(long id, HttpServletRequest request)
    //查询和删除直接待用UserService继承的类中封装的方法，较简单的逻辑直接写在Controller层，而非调用UserService接口，再调用UserServiceImp实现类
    {
        if(isAdmin(request))
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if(id <= 0)
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数错误");
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 判断是否是管理员
     * @param request 用户端传入服务端的请求
     * @return 1是管理员，2非管理员
     */
    private static boolean isAdmin(HttpServletRequest request)
    {
        Object objectUser = request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        User user = (User)objectUser;
        if(user != null || user.getUserRole() != UserConstant.ADMIN_ROLE)
        {
            return false;
        }
        return true;
    }
}
