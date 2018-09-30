package com.taxiong.tx_open_platform.serviceProvider.shop.service;

/**
 * 店铺资料
 *
 * @author szliugx@gmail.com
 * @create 2018-09-30 下午5:19
 **/
public interface ShopInfoService {
    /**
     * 获取店铺列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    String getShopInfoList(Integer page, Integer pageSize);

    /**
     * 获取店铺详情
     *
     * @param shopId
     * @return
     */
    String getShopInfoDetail(Integer shopId);
}
