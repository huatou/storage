package com.zigar.user.system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Zigar
 * @createTime 2020-01-19 10:43:05
 * @description
 */

@Configuration
public class PasswordEncoderConfiguration {

    public static final String ENCODER_ID_BCRYPT = "bcrypt";
    public static final String ENCODER_ID_MD5 = "MD5";
    public static final String ENCODER_ID_SHA1 = "SHA-1";
    public static final String ENCODER_ID_SHA256 = "SHA-256";

    @Bean
    PasswordEncoder delegatingPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


//    @Bean("BCRYPTPasswordEncoder")
//    DelegatingPasswordEncoder bCryptPasswordEncoder() {
//        Map<String, PasswordEncoder> encoders = new HashMap();
//        encoders.put(ENCODER_ID_BCRYPT, new BCryptPasswordEncoder());
//        return new DelegatingPasswordEncoder(ENCODER_ID_BCRYPT, encoders);
//    }
//
//    @Bean("MD5PasswordEncoder")
//    DelegatingPasswordEncoder MD5PasswordEncoder() {
//        Map<String, PasswordEncoder> encoders = new HashMap();
//        encoders.put(ENCODER_ID_MD5, new MessageDigestPasswordEncoder(ENCODER_ID_MD5));
//        return new DelegatingPasswordEncoder(ENCODER_ID_MD5, encoders);
//    }
//
//    @Bean("SHA1PasswordEncoder")
//    DelegatingPasswordEncoder SHA1PasswordEncoder() {
//        Map<String, PasswordEncoder> encoders = new HashMap();
//        encoders.put(ENCODER_ID_SHA1, new MessageDigestPasswordEncoder(ENCODER_ID_SHA1));
//        return new DelegatingPasswordEncoder(ENCODER_ID_SHA1, encoders);
//    }
//
//    @Bean("SHA256PasswordEncoder")
//    DelegatingPasswordEncoder SHA256PasswordEncoder() {
//        Map<String, PasswordEncoder> encoders = new HashMap();
//        encoders.put(ENCODER_ID_SHA256, new MessageDigestPasswordEncoder(ENCODER_ID_SHA256));
//        return new DelegatingPasswordEncoder(ENCODER_ID_SHA256, encoders);
//    }


}
