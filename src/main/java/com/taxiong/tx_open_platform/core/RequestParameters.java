package com.taxiong.tx_open_platform.core;

import com.taxiong.tx_open_platform.constant.SystemParameterConstant;

import java.util.HashMap;

/**
 * 请求参数处理
 *
 * @author szliugx@gmail.com
 * @create 2018-09-29 下午6:40
 **/
public class RequestParameters {

    public HashMap initParameters() {
        HashMap map = new HashMap();
        map.put(SystemParameterConstant.METHOD, null);
        map.put(SystemParameterConstant.ACCESS_TOKEN, null);
        map.put(SystemParameterConstant.APP_KEY, null);
        map.put(SystemParameterConstant.SIGN, null);
        map.put(SystemParameterConstant.TIMESTAMP, null);
        map.put(SystemParameterConstant.VERSION, null);
        return map;
    }
}
