package com.taxiong.tx_open_platform.constant;

/**
 * 错误码信息
 *
 * @author szliugx@gmail.com
 * @create 2018-07-31 下午12:21
 **/
public enum ErrorCodeMsgEnum {
    SUCCESS(0,"操作成功"),
    COMMON_ERROR(1,"操作失败"),
    METHOD_ARGUMENT_MISMATCH(2, "请求方法参数类型不匹配"),
    MISSING_SERVLET_REQUEST_PARAMETER(3, "请求参数缺失"),
    INVALID_PROPERTY(4, "无效的属性异常"),
    SYSTEM_ERROR(110, "系统繁忙");

    private String msg;
    private int code;

    ErrorCodeMsgEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
