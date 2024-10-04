package com.nagornov.scientist.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "csrf")
@Getter
@Setter
public class CsrfProperties {

    private String headerName;
    public Cookie cookie;

    @Getter
    @Setter
    public static class Cookie {

        private String name;
        private String domain;
        private String path;
        private Boolean httpOnly;
        private Boolean secure;
        private String sameSite;

    }

}
