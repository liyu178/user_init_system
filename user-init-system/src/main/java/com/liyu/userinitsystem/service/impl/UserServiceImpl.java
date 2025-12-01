package com.liyu.userinitsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyu.userinitsystem.model.constant.UserConstant;
import com.liyu.userinitsystem.model.domain.User;
import com.liyu.userinitsystem.mapper.UserMapper;
import com.liyu.userinitsystem.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author liyu
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-11-29 15:42:44
*/

//其中的返回值最终要改为封装的异常类
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService
{

    public static final String SALT = "liyuSALT";



    @Resource
    private UserMapper userMapper;

    @Override
    //用户注册
    public long userRegister(String userAccount, String userPassword, String checkPassword)
    {
        //校验
        //校验条件：用户账户、密码、校验码都不能为空
        if(StringUtils.isAllBlank(userAccount,userPassword,checkPassword))
        {
            return -1;
        }
        //校验条件:用户账户名不能小于4
        if(userAccount.length() < 4)
        {
            return -1;
        }
        //校验条件:用户密码不能小于8
        if (userPassword.length() < 8 || checkPassword.length() < 8)
        {
            return -1;
        }
        //验证条件：账户不包含殊字符
        String machPattern = "[~!@#$%^&*()_+|{}':;,.\\/<>?\"\\n\\r\\t`~!@#$%^&*()——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(machPattern).matcher(userAccount);
        if(matcher.find())
        {
            return -1;
        }
        //验证条件：密码和校验码码相同
        if(!userPassword.equals(checkPassword))
        {
            return -1;
        }
        //验证条件：账户不能重复
        //涉及到数据库查询（注意方法的前后顺序）
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        Long count = userMapper.selectCount(queryWrapper);
        if(count > 0)
        {
            return -1;
        }

        //用户密码加密
        String  encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);//王数据库中插入加密后的密码，安全
        int insert = userMapper.insert(user);//0表示插入失败，1表示插入成功
        if(insert == 0)
        {

            return -1;
        }
        return user.getId();
    }

    @Override
    //用户登录
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request)
    {
        //校验
        //校验条件：用户账户、密码、校验码都不能为空
        if(StringUtils.isAllBlank(userAccount,userPassword))
        {
            return null;
        }
        //校验条件:用户账户名不能小于4
        if(userAccount.length() < 4)
        {
            return null;
        }
        //校验条件:用户密码不能小于8
        if (userPassword.length() < 8 )
        {
            return null;
        }
        //验证条件：账户不包含殊字符
        String machPattern = "[~!@#$%^&*()_+|{}':;,.\\/<>?\"\\n\\r\\t`~!@#$%^&*()——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(machPattern).matcher(userAccount);
        if(!matcher.find())
        {
            return null;
        }
        //用户密码加密
        String  encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //涉及到数据库查询（注意方法的前后顺序）
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);//查询第一条符合的数据
        if(user == null)
        {
            log.info("用户还没有注册过");
            return null;
        }

        //用户信息脱敏
        User safeUser = getSafeUser(user);
        //记录用户登录信息
        //作用是：把登录成功的用户信息，存入当前客户端的 Session 中，标记 “用户已登录
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE,user);

        //将不重要的信息返回（脱敏后的信息）
        return safeUser;
    }

    /**
     * 用户退出登录
     * @param request
     * @return
     */
    @Override
    public int userLogout(HttpServletRequest request)
    {
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return 1;
    }

    /**
     * 用户信息脱敏
     * @param original 原始未脱敏的User对象
     * @return safeUser 脱敏后的的safeUser对象
     */
    @Override
    public  User getSafeUser(User original)
    {
        User safeUser = new User();
        safeUser.setUserAccount(original.getUserAccount());
        safeUser.setPhone(original.getPhone());
        safeUser.setUserName(original.getUserName());
        safeUser.setEmail(original.getEmail());
        safeUser.setUserRole(original.getUserRole());
        safeUser.setCreateTime(original.getCreateTime());
        return safeUser;
    }
}




