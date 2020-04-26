package com.zigar.user.utils.security;

import com.zigar.api.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


/**
 * 安全相关工具类
 *
 * @author zigar
 * @date 2020-04-26
 */

@Component
public class SecurityUtils {

    public static final UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal != null && principal instanceof UserEntity) {
                UserEntity userEntity = (UserEntity) principal;
                return userEntity;
            }
        }
        return null;
    }



}
