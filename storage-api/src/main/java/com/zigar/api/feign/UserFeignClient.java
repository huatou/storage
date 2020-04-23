package com.zigar.api.feign;

import com.zigar.api.entity.UserEntity;
import com.zigar.api.feign.fallback.UserServiceFallback;
import com.zigar.zigarcore.model.Results;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 申明这是一个Feign客户端，并且指明服务id
 *
 * @author zigar
 */
@FeignClient(value = "service-user", fallback = UserServiceFallback.class)
public interface UserFeignClient {
    /**
     * 这里定义了类似于SpringMVC用法的方法，就可以进行RESTful方式的调用了
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/zigar/user/{id}", method = RequestMethod.GET)
    Results<UserEntity> selectUserById(@PathVariable("id") Long id);


    @RequestMapping(value = "/zigar/user/{username}", method = RequestMethod.GET)
    Results<UserEntity> selectUserByUsername(@PathVariable("username") String username);
}