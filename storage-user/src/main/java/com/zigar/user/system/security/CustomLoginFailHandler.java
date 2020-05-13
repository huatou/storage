package com.zigar.user.system.security;//package com.zigar.user.system.security;

import com.alibaba.fastjson.JSON;
import com.zigar.zigarcore.model.Results;
import com.zigar.zigarcore.utils.HttpServletResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yangzihua on 2019/3/31.
 */

@Component
public class CustomLoginFailHandler implements AuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CaptchaCacheHandler captchaCacheHandler;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Results results;
        String username = httpServletRequest.getParameter("username");
        ImageCode imageCode = captchaCacheHandler.setCaptchaCache(username);
        logger.info("------》   用户为：" + username + "的验证码为：" + imageCode.getCode());
        //如果是验证失败，则需要验证码
        if (e instanceof BadCredentialsException) {
            results = Results.error("用户名或密码错误", imageCode.getImageBase64());
        } else if (e instanceof ImageCodeException) {
            results = Results.error(e.getMessage(), imageCode.getImageBase64());
        } else {
            results = Results.error(e.getMessage());
        }
        HttpServletResponseUtils.write(httpServletResponse,results);
    }
}
