package com.liyu.userinitsystem.common;

//将返回的信息、返回码以及一些补充字段封装为BaseResponse(基本相应类)
public class BaseResponse<T>
{

    /**
     * 方法成功时传入code为0
     * 方法失败时code就为ErrorCode.getCode()
     */
    private int code;

    /**
     * controller层方法的返回值类型
     * 最终要封装为data类型
     */
    private T data;

    /**
     * 补充信息
     */
    private String massage;

    /**
     * 详细描述
     */
    private String description;

    /**
     * 构造函数
     * @param code 状态码，成功为0，失败为ErrorCode.getCode()
     * @param data 数据类型
     * @param massage 补充信息
     */
    public BaseResponse(int code, T data, String massage,String description)
    {
        this.code = code;
        this.data = data;
        this.massage = massage;
        this.description = description;
    }

    public BaseResponse(int code, T data, String massage)
    {
        this.code = code;
        this.data = data;
        this.massage = massage;
    }

    public BaseResponse(int code, T data)
    {
        this.code = code;
        this.data = data;
    }

    public BaseResponse(ErrorCode errorCode)
    {
        this(errorCode.getCode(),null, errorCode.getMessage(), errorCode.getDescription() );
    }
}
