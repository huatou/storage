package com.zigar.user.config.password;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = com.zigar.user.config.password.PasswordProperties.PRE_FIX)
public class PasswordProperties {

    public static final String PRE_FIX = "zigar.password";

    private String defaultUserPassowrd = "123456";

}