package com.taxiong.tx_open_platform.core;

import com.taxiong.tx_open_platform.constant.SystemParameterConstant;
import com.taxiong.tx_open_platform.exception.BusinessException;
import com.taxiong.tx_open_platform.util.ResultUtils;
import com.taxiong.tx_open_platform.web.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import com.alibaba.fastjson.JSON;

/**
 * ApiEntranceHand API入口处理
 *
 * @author szliugx@gmail.com
 * @create 2018-08-08 下午6:07
 **/
@Component
@Slf4j
public class ApiEntranceHand implements InitializingBean, ApplicationContextAware {
    ApiMethodStore apiMethodStore;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        apiMethodStore = new ApiMethodStore(applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        apiMethodStore.loadApiFromSpringBeans();
    }

    /**
     * 处理请求
     *
     * @param request
     * @return
     */
    public Result handle(HttpServletRequest request) {
        RequestParameters requestParameters = new RequestParameters();
        HashMap<String, String> parameters = requestParameters.initParameters();
        // 获取所有的参数名
        Enumeration<String> parameterNames = request.getParameterNames();
        for (Enumeration<String> e = parameterNames; e.hasMoreElements(); ) {
            String thisName = e.nextElement().toString();
            parameters.put(thisName, request.getParameter(thisName));
        }
        String apiName = parameters.get(SystemParameterConstant.METHOD);
        // 判断系统参数是否存在并合法，存在从hashMap中删除，留下的为应用参数
        parameters = checkSystemParams(parameters);

        ApiMethodStore.ApiRunnable apiRunnable;

        apiRunnable = apiMethodStore.findApiRunnable(apiName);
        if (apiRunnable == null) {

            throw new BusinessException(3, "调用方法不存在");
        }

        Object[] args = buildMethodParams(apiRunnable.getParameterNameAndParameterType(), parameters);

        Result result;
        try {

            result = ResultUtils.success(apiRunnable.run(args));
        } catch (IllegalAccessException e) {
            String msg = "异常访问";
            throw new BusinessException(2, e.getMessage() != null ? e.getMessage() : msg);
        } catch (InvocationTargetException e) {
            String msg = "调用目标异常";
            Throwable targetEx = ((InvocationTargetException) e).getTargetException();
            if (targetEx != null) {
                msg = targetEx.getMessage();
            }
            throw new BusinessException(2, msg);
        }

        return result;
    }

    /**
     * 校验系统参数
     *
     * @param requestParams
     * @return
     */
    public HashMap<String, String> checkSystemParams(HashMap<String, String> requestParams) {
        String method = requestParams.get(SystemParameterConstant.METHOD);
        String access_token = requestParams.get(SystemParameterConstant.ACCESS_TOKEN);
        String app_key = requestParams.get(SystemParameterConstant.APP_KEY);
        String sign = requestParams.get(SystemParameterConstant.SIGN);
        String timestamp = requestParams.get(SystemParameterConstant.TIMESTAMP);
        String version = requestParams.get(SystemParameterConstant.VERSION);
        // 系统参数是否必填；签名是否正确

        // 删除后留下的参数为应用级别的参数
        requestParams.remove(SystemParameterConstant.METHOD);
        requestParams.remove(SystemParameterConstant.ACCESS_TOKEN);
        requestParams.remove(SystemParameterConstant.APP_KEY);
        requestParams.remove(SystemParameterConstant.SIGN);
        requestParams.remove(SystemParameterConstant.TIMESTAMP);
        requestParams.remove(SystemParameterConstant.VERSION);

        return requestParams;
    }

    /**
     * 构建访问应用的参数
     *
     * @param
     * @param requestMethodParameters
     * @return
     */
    private Object[] buildMethodParams(List<List<Object>> parameterNameAndParameterTypes, HashMap<String, String> requestMethodParameters) {
        // 应用启动时
        int parameterLength = parameterNameAndParameterTypes.size();
        Object[] args = new Object[parameterLength];
        int i = 0;
        for (final List<Object> parameterNameAndParameterType : parameterNameAndParameterTypes) {
            String paramName = (String) parameterNameAndParameterType.get(0);
            if (requestMethodParameters.containsKey(paramName)) {
                args[i] = JSON.parseObject(requestMethodParameters.get(paramName), (Class<? extends Object>) parameterNameAndParameterType.get(1));
            } else {
                args[i] = null;
            }
            i++;
        }

        return args;
    }
}
