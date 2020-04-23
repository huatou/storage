package com.zigar.oauth2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        String finalSecret = passwordEncoder.encode("123456");

        /**
         * Oauth支持的5类grant_type
         * 1、authorization_code 授权码模式(即先登录获取code,再获取token)
         * 2、password           密码模式(将用户名,密码传过去,直接获取token)
         * 3、client_credentials 客户端模式(无用户,用户向客户端注册,然后客户端以自己的名义向’服务端’获取资源)
         * 4、implicit           简化模式(在redirect_uri 的Hash传递token; Auth客户端运行在浏览器中,如JS,Flash)
         * 5、refresh_token      刷新access_token
         */

        clients.inMemory()
                .withClient("client_1").authorizedGrantTypes("authorization_code")
                .secret(finalSecret)
                .scopes("select", "insert", "edit", "delete")
                .and()

                /**
                 * password模式需要在endpoints配置AuthenticationManager
                 */
                .withClient("client_2")
                .authorizedGrantTypes("password")
                .secret(finalSecret)
                .scopes("select", "insert", "edit", "delete")
                .and()

                /**
                 * 不能配置WebSecurityConfigurerAdapter的configure的http
                 */
                .withClient("client_3")
                .authorizedGrantTypes("client_credentials")
                .scopes("select", "insert", "edit", "delete")
                .authorities("oauth2")
                .secret(finalSecret)
                .and()

                .withClient("client_5")
                .authorizedGrantTypes("refresh_token")
                .scopes("select", "insert", "edit", "delete")
                .secret(finalSecret);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager)//gruntType=password时需要配置，不然会报gruntType不存在
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()    //允许表单认证
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

}