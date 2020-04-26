package com.zigar.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zigar.api.entity.UserEntity;
import com.zigar.user.config.password.PasswordProperties;
import com.zigar.user.mapper.UserMapper;
import com.zigar.user.service.IUserService;
import com.zigar.user.utils.jwt.JwtToken;
import com.zigar.user.utils.jwt.JwtTokenUtil;
import com.zigar.zigarcore.model.Results;
import com.zigar.zigarcore.myabtisplus.ZServiceImpl;
import com.zigar.zigarcore.utils.Assert;
import com.zigar.zigarcore.utils.StringUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;

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

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public void saveOrUpdateUser(UserEntity userEntity) {
        Assert.notNull(userEntity, "userEntity 不能为空");
        String userId = userEntity.getUserId();
        if (StringUtils.isBlank(userId)) {
            //userId为空表示是插入用户
            Assert.notNull(userEntity.getUsername(), "username 不能为空");
            Assert.notNull(userEntity.getDisplayName(), "displayName 不能为空");
            String password = userEntity.getPassword();
            if (StringUtils.isNotBlank(password)) {
                String encodePassword = passwordEncoder.encode(password);
                userEntity.setPassword(encodePassword);
            } else {
                String defaultUserPassword = passwordProperties.getDefaultUserPassowrd();
                String encoderDefaultUserPassword = passwordEncoder.encode(defaultUserPassword);
                userEntity.setPassword(encoderDefaultUserPassword);
            }
        }
        this.saveOrUpdate(userEntity);
    }

    @Override
    public Results<String> userLogin(UserEntity userEntity) {

        Assert.notNull(userEntity, "userEntity 不能为空");
        Assert.notNull(userEntity.getUsername(), "username 不能为空");
        Assert.notNull(userEntity.getPassword(), "password 不能为空");
        LambdaQueryWrapper<UserEntity> userEntityLambdaQueryWrapper = Wrappers.<UserEntity>query().lambda().eq(UserEntity::getUsername, userEntity.getUsername());
        UserEntity localUser = getOne(userEntityLambdaQueryWrapper);
        if (localUser == null) {
            return Results.error("用户名不存在");
        } else {
            boolean matches = passwordEncoder.matches(userEntity.getPassword(), localUser.getPassword());
            if (matches) {
                JwtToken jwtToken = jwtTokenUtil.generateToken(userEntity.getUsername(), userEntity.getUserId());
                return Results.succeed(jwtToken.getToken());
            } else {
                return Results.error("密码错误");
            }
        }
    }

}
