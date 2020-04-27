package com.zigar.user.service.impl;

import com.zigar.api.entity.UserEntity;
import com.zigar.user.mapper.UserMapper;
import com.zigar.user.model.RegisterUser;
import com.zigar.user.service.IUserService;
import com.zigar.user.system.password.PasswordProperties;
import com.zigar.zigarcore.model.Results;
import com.zigar.zigarcore.myabtisplus.ZServiceImpl;
import com.zigar.zigarcore.utils.DateUtils;
import com.zigar.zigarcore.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author zigar
 * @since 2020-03-31
 */
@Service
public class UserServiceImpl extends ZServiceImpl<UserMapper, UserEntity> implements IUserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PasswordProperties passwordProperties;

    @Override
    public void insertUser(@NotNull UserEntity userEntity) {
        String password = userEntity.getPassword();
        String encodePassword = passwordEncoder.encode(password);
        userEntity.setPassword(encodePassword);
        this.saveOrUpdate(userEntity);
    }

    @Override
    public void updateUser(@NotNull UserEntity userEntity) {
        /**
         * 修改密码，密码必须加密
         */
        String password = userEntity.getPassword();
        if (StringUtils.isNotBlank(password)) {
            String encodePassword = passwordEncoder.encode(password);
            userEntity.setPassword(encodePassword);
            userEntity.setPwdResetTime(DateUtils.now());
        }
        this.saveOrUpdate(userEntity);
    }

    @Override
    public Results userRegister(@NotNull RegisterUser registerUser) {
        UserEntity registerUserEntity = new UserEntity(registerUser.getUsername(), registerUser.getPassword());
        insertUser(registerUserEntity);
        return Results.succeed();
    }

}
