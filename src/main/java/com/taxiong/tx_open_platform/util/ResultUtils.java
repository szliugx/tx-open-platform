package com.taxiong.tx_open_platform.util;

import com.taxiong.tx_open_platform.web.vo.Result;

/**
 * 返回工具类
 *
 * @author szliugx@gmail.com
 * @create 2018-05-23 下午12:48
 **/
public class ResultUtils {

    /**
     * 返回正确带data的
     *
     * @param object
     * @return
     */
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("success");
        result.setData(object);

        return result;
    }

    /**
     * 返回正确不带data
     *
     * @return
     */
    public static Result success() {

        return success(null);
    }

    /**
     * 返回错误带编码和提示信息的
     *
     * @param code
     * @param msg
     * @return
     */
    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);

        return result;
    }
}
