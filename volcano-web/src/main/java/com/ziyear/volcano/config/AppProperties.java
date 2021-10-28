package com.ziyear.volcano.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * 功能描述 : TODO
 *
 * @author you_name 2021-10-27 22:03
 */
@Configuration
@ConfigurationProperties(prefix = "volcano")
public class AppProperties {

    @Getter
    @Setter
    @Valid
    private Jwt jwt = new Jwt();

    @Getter
    @Setter
    private SmsProvider smsProvider = new SmsProvider();

    @Getter
    @Setter
    private EmailProvider emailProvider = new EmailProvider();

    @Getter
    @Setter
    @Valid
    private Tx tx = new Tx();

    @Getter
    @Setter
    public static class Jwt {
        private String header = "Authorization"; // HTTP 报头的认证字段的 key

        private String prefix = "Bearer "; // HTTP 报头的认证字段的值的前缀

        @Min(5000L)
        private long accessTokenExpireTime = 60 * 1000L; // Access Token 过期时间

        @Min(3600000L)
        private long refreshTokenExpireTime = 30 * 24 * 3600 * 1000L; // Refresh Token 过期时间
    }

    @Getter
    @Setter
    public static class SmsProvider {
        private String name;
        private String apiUrl;
    }


    @Getter
    @Setter
    public static class Tx {
        private String appId;
        private String secretId;
        private String secretKey;
    }

    @Getter
    @Setter
    public static class EmailProvider {
        private String name;
        private String apiKey;
    }
}
