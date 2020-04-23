package com.zigar.api.feign.fallback;

import com.zigar.api.entity.UserEntity;
import com.zigar.api.feign.UserFeignClient;
import com.zigar.zigarcore.model.Results;
import org.springframework.stereotype.Service;

/**
 * @author Zigar
 * @createTime 2020-01-30 20:01:40
 * @description
 */

@Service
public class UserServiceFallback implements UserFeignClient {

    @Override
    public Results<UserEntity> selectUserById(Long id) {
        UserEntity userEntity = new UserEntity();
        userEntity.setDisplayName("触发熔断器[selectUserById]，获取displayName失败");
        userEntity.setUsername("触发熔断器[selectUserById]，获取username失败");
        return Results.error("触发熔断器[selectUserById]", userEntity);
    }

    @Override
    public Results<UserEntity> selectUserByUsername(String username) {
        UserEntity userEntity = new UserEntity();
        userEntity.setDisplayName("触发熔断器[selectUserByUsername]，获取displayName失败");
        userEntity.setUsername("触发熔断器[selectUserByUsername]，获取username失败");
        return Results.error("触发熔断器[selectUserByUsername]", userEntity);
    }

}
