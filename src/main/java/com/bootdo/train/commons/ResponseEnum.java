package com.bootdo.train.commons;

/**
 * 枚举类  方便管理
 *
 * 服务器响应对象， 统一管理状态
 */
public enum ResponseEnum {

    SUCCESS(0,"成功"),
    ERROR(1,"错误"),
    NEED_LOGIN(10,"需要登入"),
    ILLEGAL_ARGUMENT(2,"非法参数")


    ;
    private final int code;
    private final String msg;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }



}
