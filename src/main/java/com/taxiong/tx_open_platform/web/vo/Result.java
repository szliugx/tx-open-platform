package com.taxiong.tx_open_platform.web.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * api返回给客户端的对象
 *
 * @author szliugx@gmail.com
 * @create 2018-05-23 下午12:44
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;
}
