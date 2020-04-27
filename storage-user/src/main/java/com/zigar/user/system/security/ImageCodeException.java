package com.zigar.user.system.security;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义验证码错误异常
 *
 * @author zigar
 * @date 2020-04-27
 */
public class ImageCodeException extends AuthenticationException {

    public ImageCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ImageCodeException(String msg) {
        super(msg);
    }
}
