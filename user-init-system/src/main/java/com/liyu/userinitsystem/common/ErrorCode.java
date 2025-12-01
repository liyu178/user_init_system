package com.liyu.userinitsystem.common;

public enum ErrorCode
{
    //枚举常量是 “不可变的”，code/message/description 一旦通过构造方法初始化，就不允许修改；所以没有setter方法
    SUCCESS(0,"succss",""),
    PARAMS_ERROR(4000,"请求参数错误",""),
    NULL_ERROR(4001,"请求参数为空",""),
    NOT_LOGIN(40100,"未登录",""),
    NO_AUTHORITY(401011,"无权限","不是管理员，所以您的操作无效"),
    SYSTEM_ERROR(5000,"系统内部错误","");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态信息
     */
    private final String message;

    /**
     * 状态信息详细描述
     */
    private final String description;

    ErrorCode(int code, String message, String description)
    {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode()
    {
        return code;
    }

    public String getMessage()
    {
        return message;
    }

    public String getDescription()
    {
        return description;
    }
}
