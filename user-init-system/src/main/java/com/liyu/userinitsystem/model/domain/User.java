package com.liyu.userinitsystem.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户表
 *
 * @TableName user
 */
@Data
@TableName(value = "user")
public class User
{
    /**
     * 用户id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户账户
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 电话
     */
    private String phone;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户角色(默认为0)
     */
    private Integer userRole;

    /**
     * 用户创建时间
     */
    private Date createTime;

    /**
     * 更新时间（面向所有）
     */
    private Date updateTime;

    /**
     * 用户编辑时间（只面向用户）
     */
    private Date editTime;

    /**
     * 是否删除（逻辑删除）0--未删除
     */
    @TableLogic
    private Integer isDelete;

}