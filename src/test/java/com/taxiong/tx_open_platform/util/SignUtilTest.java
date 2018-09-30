package com.taxiong.tx_open_platform.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SignUtilTest {

    @Test
    public void signTopRequest() {
        HashMap map = new HashMap();
        map.put("userId", "123");
        map.put("name", "zhangsan");
        map.put("pwd", "DHJKSHDKJ");
        map.put("abc", "ad");
        String sign="";
        try {
            sign = SignUtil.signTopRequest(map, "-密码-", "hmac");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf(sign);
        assertTrue(sign != "");
    }
}