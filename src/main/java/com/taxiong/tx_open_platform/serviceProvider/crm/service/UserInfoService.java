package com.taxiong.tx_open_platform.serviceProvider.crm.service;

import com.taxiong.tx_open_platform.annotation.methodParameter.NoValue;

import javax.validation.constraints.NotNull;

/**
 * 获取用户信息接口
 *
 * @author szliugx@gmail.com
 * @create 2018-09-29 下午11:03
 **/
public interface UserInfoService {

    /**
     * 获取用户列表
     *
     * @param Page
     * @param PageSize
     * @return
     */
    String getUserList(Integer Page, Integer PageSize);

    /**
     * 获取用户信息详情
     *
     * @param userId
     * @return
     */
    String getUserInfoDetail(Integer userId);
}
