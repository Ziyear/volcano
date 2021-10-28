package com.ziyear.volcano.service;

import com.ziyear.volcano.common.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能描述 : TODO
 *
 * @author zhaorui 2021-10-28 14:07
 */
public class SmsTest extends BaseIntegrationTest {

    @Autowired
    private SmsService smsService;


    @Test
    public void sendTest() {
        //smsService.send("+8618538566073","000000");
    }
}
