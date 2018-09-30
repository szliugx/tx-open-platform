package com.taxiong.tx_open_platform.serviceProvider.shop.service.impl;

import com.taxiong.tx_open_platform.core.ApiMethodMapping;
import com.taxiong.tx_open_platform.serviceProvider.shop.service.ShopInfoService;
import org.springframework.stereotype.Service;

/**
 * 店铺资料接口实现
 *
 * @author szliugx@gmail.com
 * @create 2018-09-30 下午5:21
 **/
@Service
public class ShopInfoServiceImpl implements ShopInfoService {
    /**
     * 获取店铺列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    @ApiMethodMapping("taxiong.shop.info.list")
    public String getShopInfoList(Integer page, Integer pageSize) {
        return "demo店铺资料列表第" + page + "页，每页" + pageSize + "条";
    }

    /**
     * 获取店铺详情
     *
     * @param shopId
     * @return
     */
    @Override
    @ApiMethodMapping("taxiong.shop.info.detail")
    public String getShopInfoDetail(Integer shopId) {
        return "这是第" + shopId + "号店铺资料详情";
    }
}
