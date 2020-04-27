package com.zigar.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Zigar
 * @createTime 2020-01-15 11:21:06
 * @description
 */

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.zigar.user.service.impl",
        "com.zigar.user.rest",
        "com.zigar.user.system.cache",
        "com.zigar.user.system.password",
        "com.zigar.user.system.security",
        "com.zigar.user.utils"})
@MapperScan("com.zigar.user.mapper")
@EnableEurekaClient
public class ServiceUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceUserApplication.class, args);
    }

}
