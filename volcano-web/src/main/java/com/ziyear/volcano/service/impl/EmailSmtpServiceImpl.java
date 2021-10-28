package com.ziyear.volcano.service.impl;

import com.ziyear.volcano.service.EmailService;
import com.ziyear.volcano.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 功能描述 : TODO
 *
 * @author zhaorui 2021-10-28 14:25
 */

@ConditionalOnProperty(prefix = "volcano.email-provider", name = "name", havingValue = "smtp")
@RequiredArgsConstructor
@Service
public class EmailSmtpServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender emailSender;

    @Override
    public void send(String email, String msg) {
        val message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(from);
        message.setSubject("App volcano Verification code");
        message.setText("您的验证码为:" + msg + ", 有效期" + Constants.VERIFY_CODE_VALID_MINUTES + "分钟。");
        emailSender.send(message);
    }
}
