package com.taxiong.tx_open_platform.web.controller;

import com.taxiong.tx_open_platform.core.ApiEntranceHand;
import com.taxiong.tx_open_platform.web.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 接入控制器
 *
 * @author szliugx@gmail.com
 * @create 2018-09-30 下午12:37
 **/
@RestController
@Slf4j
public class ApiController {
    @Autowired
    ApiEntranceHand apiEntranceHand;

    @PostMapping("/api")
    public Result run(HttpServletRequest request) {

        return apiEntranceHand.handle(request);
    }
}
