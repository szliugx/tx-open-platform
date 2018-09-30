package com.taxiong.tx_open_platform.exception;


import com.taxiong.tx_open_platform.constant.ErrorCodeMsgEnum;
import com.taxiong.tx_open_platform.util.ResultUtils;
import com.taxiong.tx_open_platform.web.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 统一异常处理类
 *
 * @author szliugx@gmail.com
 * @create 2018-05-23 下午12:41
 **/

@ControllerAdvice
@Slf4j
public class Handler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;

            return ResultUtils.error(businessException.getCode(), businessException.getMessage());
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            // 参数类型

            return ResultUtils.error(ErrorCodeMsgEnum.METHOD_ARGUMENT_MISMATCH.getCode(),
                    ErrorCodeMsgEnum.METHOD_ARGUMENT_MISMATCH.getMsg());
        } else if (e instanceof MissingServletRequestParameterException) {
            // 必须传的参数未传

            return ResultUtils.error(ErrorCodeMsgEnum.MISSING_SERVLET_REQUEST_PARAMETER.getCode(),
                    ErrorCodeMsgEnum.MISSING_SERVLET_REQUEST_PARAMETER.getMsg());
        } else if (e instanceof InvalidPropertyException){
            // 无效的属性
            return ResultUtils.error(ErrorCodeMsgEnum.INVALID_PROPERTY.getCode(),
                    ErrorCodeMsgEnum.INVALID_PROPERTY.getMsg());
        }else{

            log.error("[{}]{}", ErrorCodeMsgEnum.SYSTEM_ERROR.getMsg(), e);

            return ResultUtils.error(ErrorCodeMsgEnum.SYSTEM_ERROR.getCode(), ErrorCodeMsgEnum.SYSTEM_ERROR.getMsg());
        }
    }
}
