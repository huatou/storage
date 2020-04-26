package com.zigar.user.security;//package com.zigar.user.system.security;

import com.zigar.zigarcore.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class ImageCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private String captchaParameter = SPRING_SECURITY_FORM_CAPTCHA_KEY;

    private boolean postOnly = true;

    @Autowired
    CaptchaCacheHandler cacheCaptchaHandler;

    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    @Autowired
    private CustomLoginFailHandler customLoginFailHandler;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    SecurityProperties securityProperties;

    public ImageCodeAuthenticationFilter(SecurityProperties securityProperties) {
        super(new AntPathRequestMatcher(securityProperties.getLoginProcessingUrl(), securityProperties.getLoginMethod()));
    }

    @Override
    public void afterPropertiesSet() {
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(customLoginSuccessHandler);
        setAuthenticationFailureHandler(customLoginFailHandler);
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {

            String username = this.obtainUsername(request);
            String password = this.obtainPassword(request);
            if (StringUtils.isEmpty(username)) {
                throw new UsernameNotFoundException("用户名不能为空");
            }

            if (StringUtils.isEmpty(password)) {
                password = "";
            }

            //检查当前用户是否存在验证失败的cache
            String cacheCaptcha = cacheCaptchaHandler.getCaptchaCacheByUsername(username);
            if (!StringUtils.isEmpty(cacheCaptcha)) {
                String captcha = this.obtainCaptcha(request);
                logger.info("------》   用户为：" + username + "的验证码为：" + cacheCaptcha + "；输入的验证码为：" + captcha);
                if (StringUtils.isEmpty(captcha)) {
                    throw new UsernameNotFoundException("验证码不能为空");
                } else if (!captcha.equalsIgnoreCase(cacheCaptcha)) {
                    throw new UsernameNotFoundException("验证码不正确");
                }
            }

            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(this.passwordParameter);
    }

    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(this.usernameParameter);
    }

    protected String obtainCaptcha(HttpServletRequest request) {
        return request.getParameter(this.captchaParameter);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    public void setCaptchaParameter(String captchaParameter) {
        Assert.hasText(captchaParameter, "Captcha parameter must not be empty or null");
        this.passwordParameter = captchaParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getUsernameParameter() {
        return this.usernameParameter;
    }

    public final String getPasswordParameter() {
        return this.passwordParameter;
    }

    public final String getCaptchaParameter() {
        return this.captchaParameter;
    }
}