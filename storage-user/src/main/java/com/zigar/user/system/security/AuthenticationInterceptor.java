package com.zigar.user.system.security;//package com.zigar.user.system.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zigar.api.entity.UserEntity;
import com.zigar.user.service.IUserService;
import com.zigar.user.utils.jwt.JwtTokenUtil;
import com.zigar.user.utils.jwt.PassToken;
import com.zigar.user.utils.security.SecurityUtils;
import com.zigar.zigarcore.exception.BusinessLogicException;
import com.zigar.zigarcore.properties.JwtProperties;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    RedisCacheManager redisCacheManager;

    @Autowired
    IUserService userService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    JwtProperties jwtProperties;

    /**
     * 加了@PassToken注解，表示该方法不需要通过登录也可访问
     * 没有加任何注解的方法，需要通过登录才可以访问
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param object
     * @return
     * @throws Exception
     */

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有@PassToken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解

        // 从http请求头中取出token
        String bearerToken = httpServletRequest.getHeader(jwtProperties.getJwtHttpHeader());
        if (!StringUtils.isEmpty(bearerToken)) {
            // 获取符合bearer规范的token
            String token = jwtTokenUtil.getTokenFromBearerToken(bearerToken);
            if (StringUtils.isEmpty(token)) {
                throw new BusinessLogicException("此令牌不符合bearer规范。");
            }

            //通过解析token获取用户id
            Long userId;
            try {
                userId = jwtTokenUtil.getUserIdFromToken(token);
                if (userId == null) {
                    throw new BusinessLogicException("此令牌无对应的用户。");
                }
            } catch (ExpiredJwtException e) {
                throw new BusinessLogicException("使用的令牌已经过期。");
            } catch (RuntimeException e) {
                throw new BusinessLogicException("无法解析请求的令牌。");
            }

            //根据userId从数据库查询出user实体

            LambdaQueryWrapper<UserEntity> userLambdaQueryWrapper = Wrappers.<UserEntity>lambdaQuery()
                    .select(UserEntity.class, i -> !StringUtils.equals(i.getProperty(), "password"))
                    .eq(UserEntity::getUserId, userId);

            UserEntity currentUser = userService.getOne(userLambdaQueryWrapper);
            if (currentUser != null) {
                //验证该token是否过期
                boolean tokenIsValidated = jwtTokenUtil.validateToken(token, currentUser);
                if (!tokenIsValidated) {
                    throw new BusinessLogicException("使用的令牌已经过期。");
                } else {
                    //token未过期后保存当前用户信息到SecurityContextHolder
                    SecurityUtils.setCurrentUser(currentUser);
                    return true;
                }
            } else {
                throw new BusinessLogicException("该用户已不存在");
            }
        } else {
            throw new BusinessLogicException("此请求需要验证令牌信息");
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

//    private void tokenExpire(HttpServletResponse httpServletResponse, String message) throws IOException {
//        Results results = Results.error(message);
//        httpServletResponse.setContentType("application/json;charset=utf-8");
//        httpServletResponse.setStatus(403);
//        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
//        httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//        httpServletResponse.getOutputStream().write(JSON.toJSONString(results).getBytes());
//    }
}