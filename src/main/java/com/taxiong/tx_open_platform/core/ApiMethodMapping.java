package com.taxiong.tx_open_platform.core;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ApiMethodMapping {
    /**
     * 接口名称
     *
     * @return
     */
    String value();

    /**
     * 是否需要登录
     *
     * @return
     */
    boolean checkLogin() default false;
}
