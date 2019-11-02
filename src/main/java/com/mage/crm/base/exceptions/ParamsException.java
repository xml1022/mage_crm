package com.mage.crm.base.exceptions;

/**
 * 定义一个运行时异常，用于抛出异常
 */
public class ParamsException extends RuntimeException{
    private Integer code;//状态码
    private String msg;//提示信息

    public ParamsException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
