package com.zigar.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author Zigar
 * @createTime 2020-03-03 17:22:24
 * @description
 */

@SpringBootApplication
@EnableEurekaClient
@ComponentScan({"com.zigar.oauth2.controller", "com.zigar.oauth2.security"})
@EnableResourceServer
public class ServerOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(ServerOauth2Application.class, args);
    }

}