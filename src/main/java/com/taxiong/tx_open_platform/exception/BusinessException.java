package com.taxiong.tx_open_platform.exception;

/**
 * 自定义的业务异常
 *
 * @author szliugx@gmail.com
 * @create 2018-05-23 下午1:06
 **/
public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
