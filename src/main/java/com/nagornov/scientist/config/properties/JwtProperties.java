package com.nagornov.scientist.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtProperties {

    private String accessSecret;
    private String refreshSecret;
    private Integer accessExpireMinutes;
    private Integer refreshExpireDays;

}
