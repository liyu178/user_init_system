package com.liyu.userinitsystem.common;


 //静态工具类，封装了创建 BaseResponse 实例的逻辑，无需手动 new BaseResponse(...)，简化接口返回代码。
public class ResultUtils<T>
{
    /**
     * 成功
     * @param data controller层调用方法的返回值类型<T>
     */
    public static <T> BaseResponse<T> success(T data)//第一个<T>为占位符，避免编译器把后面的 T 当成一个 “已存在的具体类”（比如 java.lang.T
    {
        return new BaseResponse<>(0,data,"success","");
    }

    /**
     * 失败
     * @param errorCode controller层的返回结果传入一个错误码
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode)
    {
        return new BaseResponse<>(errorCode.getCode(), null,errorCode.getMessage(), errorCode.getDescription());
    }

    /**
     * 失败
     */
    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse(code, null, message, description);
    }

    /**
     * 失败
     */
    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse(errorCode.getCode(), null, message, description);
    }

    /**
     * 失败
     */
    public static BaseResponse error(ErrorCode errorCode, String description) {
        return new BaseResponse(errorCode.getCode(), errorCode.getMessage(), description);
    }

}
