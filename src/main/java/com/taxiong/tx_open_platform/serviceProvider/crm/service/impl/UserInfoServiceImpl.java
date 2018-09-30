package com.taxiong.tx_open_platform.serviceProvider.crm.service.impl;

import com.taxiong.tx_open_platform.annotation.methodParameter.NoValue;
import com.taxiong.tx_open_platform.core.ApiMethodMapping;
import com.taxiong.tx_open_platform.exception.BusinessException;
import com.taxiong.tx_open_platform.serviceProvider.crm.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 获取用户详情接口实现
 *
 * @author szliugx@gmail.com
 * @create 2018-09-29 下午11:05
 **/
@Service
public class UserInfoServiceImpl implements UserInfoService {
    /**
     * 获取用户列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    @ApiMethodMapping(value = "taxiong.crm.user.list")
    public String getUserList(Integer page, Integer pageSize) {
        return "当前请求参数为第:" + page + "页，每页第" + pageSize + "条";
    }

    /**
     * 获取用户信息详情
     *
     * @param userId
     * @return
     */
    @Override
    @ApiMethodMapping(value = "taxiong.crm.user.get")
    public String getUserInfoDetail(Integer userId) {
        if (userId == null) {
            throw new BusinessException(5, "用户必须为空");
        } else {
            return "您的用户id为:" + userId;
        }
    }
}
