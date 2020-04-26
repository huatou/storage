package com.zigar.user.security;//package com.zigar.user.system.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class CaptchaCacheHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String CAPTCHA_CACHE_NAME = "CAPTCHA_CACHE_NAME";

    @Autowired
    private RedisTemplate redisTemplate;

    public String getCaptchaCacheByUsername(String username) {

        String key = getCaptchaKeyByUserName(username);
        Object result = redisTemplate.opsForValue().get(key);
        if (result != null) {
            return result.toString();
        }
        return null;
    }


    public ImageCode setCaptchaCache(String username) throws IOException {
        ImageCode imageCode = ImageCodeUtils.createImageCode();
        String key = getCaptchaKeyByUserName(username);
        redisTemplate.opsForValue().set(key, imageCode.getCode());
        return imageCode;
    }

    //清空验证码缓存
    public void clearCaptcha(String username) {
        String key = getCaptchaKeyByUserName(username);
        redisTemplate.delete(key);
    }


    private String getCaptchaKeyByUserName(String username) {
        StringBuilder cacheKey = new StringBuilder();
        cacheKey.append(CAPTCHA_CACHE_NAME);
        cacheKey.append("_");
        cacheKey.append(username);
        return cacheKey.toString();
    }


}