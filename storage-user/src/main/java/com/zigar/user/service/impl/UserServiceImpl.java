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
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Date;

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
        String username = userEntity.getUsername();
        String password = userEntity.getPassword();
        Assert.notNull(username, "username不能为空");
        Assert.notNull(password, "password不能为空");
        String encodePassword = passwordEncoder.encode(password);
        userEntity.setPassword(encodePassword);
        userEntity.setPwdResetTime(DateUtils.now());
        userEntity.setIsAccountNonExpired(true);
        userEntity.setIsAccountNonLocked(true);
        userEntity.setIsCredentialsNonExpired(true);
        userEntity.setIsEnabled(true);
        this.saveOrUpdate(userEntity);
    }

    @Override
    public void updateUser(@NotNull UserEntity userEntity) {
        Assert.notNull(userEntity.getUserId(), "userId不能为空");
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
    public void userRegister(@NotNull RegisterUser registerUser) {
        String username = registerUser.getUsername();
        String password = registerUser.getPassword();
        Assert.notNull(username, "username不能为空");
        Assert.notNull(password, "password不能为空");
        UserEntity registerUserEntity = new UserEntity(username, password);
        insertUser(registerUserEntity);
    }

}
