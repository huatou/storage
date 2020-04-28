package com.zigar.user.system.security;//package com.zigar.user.system.security;

import com.zigar.zigarcore.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(securityProperties.getPermitAllUrls());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true).maxAge(3600);
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //该配置为默认配置，contextPath/后为静态资源路径，省略/static
////        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
////
//        //增加静态资源访问路径contextPath/static/
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//    }


    //    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        /**
//         * url指定的路径映射到真实的服务器路径
//         * 自动配置内容为：urlPattern=/**，resource为classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/
//         *
//         * 此处配置的urlPattern为所有的静态资源访问路径，如果不经过这些路径无法访问静态资源
//         *
//         */
//
//        //该配置为默认配置，contextPath/后为静态资源路径，省略/static
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//
//        //增加静态资源访问路径contextPath/static/
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//
//        //增加静态资源访问路径contextPath/swagger-ui.html，该swagger-ui.html文件在classpath:/META-INF/resources/
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//
//        //增加静态资源访问路径contextPath/webjars/，webjar用途：把静态资源打包成maven
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//        super.addResourceHandlers(registry);
//    }

}