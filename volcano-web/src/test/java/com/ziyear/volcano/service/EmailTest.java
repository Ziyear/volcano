package com.ziyear.volcano.service;

import com.ziyear.volcano.common.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能描述 : TODO
 *
 * @author zhaorui 2021-10-28 14:43
 */
public class EmailTest extends BaseIntegrationTest {
    @Autowired
    private EmailService emailService;

    @Test
    public void sendTest() {
        emailService.send("962334216@qq.com", "000000");
    }
}
