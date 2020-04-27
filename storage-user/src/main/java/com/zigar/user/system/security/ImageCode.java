package com.zigar.user.system.security;

import java.time.LocalDateTime;

/**
 * 封装验证码信息
 * Created by zigar on 2017/12/1.
 */
public class ImageCode {

    /**
     * 图片 展示用 base64
     */
    private String imageBase64;
    /**
     * 随机数
     */
    private String code;
    /**
     * 过期时间
     */
    private LocalDateTime localDateTime;

    public ImageCode(String imageBase64, String code, int second) {
        this.imageBase64 = imageBase64;
        this.code = code;
        // 多少秒后
        this.localDateTime = LocalDateTime.now().plusSeconds(second);
    }

    public ImageCode(String imageBase64, String code, LocalDateTime localDateTime) {
        this.imageBase64 = imageBase64;
        this.code = code;
        this.localDateTime = localDateTime;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}