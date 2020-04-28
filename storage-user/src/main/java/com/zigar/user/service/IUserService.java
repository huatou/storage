package com.zigar.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zigar.api.entity.UserEntity;
import com.zigar.user.model.RegisterUser;
import com.zigar.zigarcore.action.RequestInsertAction;
import com.zigar.zigarcore.model.Results;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author zigar
 * @since 2020-03-31
 */
public interface IUserService extends IService<UserEntity> {

    /**
     * 新增用户
     *
     * @param userEntity
     */
    void insertUser(@NotNull @Validated(RequestInsertAction.class) UserEntity userEntity);

    /**
     * 更新用户
     *
     * @param userEntity
     */
    void updateUser(@NotNull UserEntity userEntity);

    /**
     * 用户注册
     *
     * @param registerUser
     * @return
     */
    void userRegister(@NotNull RegisterUser registerUser);

}
