//package com.zigar.user.security;//package com.zigar.user.system.security;
//
//import com.zigar.api.entity.UserEntity;
//import com.zigar.user.system.utils.request.RequestUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @author Zigar
// * @createTime 2020-01-17 11:09:51
// * @description
// */
//
//@Component
//public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Override
//    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//        UserEntity currentUser = RequestUtils.getCurrentUser();
//        if (currentUser != null) {
//            logger.info("------》   用户为：" + currentUser.getUsername() + "登出成功");
//        } else {
//            logger.info("------》   登出后已删除当前用户信息");
//        }
//    }
//}
