package com.zigar.user.service.impl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zigar.api.entity.UserEntity;
import com.zigar.api.util.Unique;
import com.zigar.core.exception.BusinessLogicException;
import com.zigar.core.utils.Assert;
import com.zigar.core.utils.StringUtils;
import com.zigar.user.config.password.PasswordProperties;
import com.zigar.user.mapper.UserMapper;
import com.zigar.user.service.IUserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void saveOrUpdateUser(UserEntity userEntity) {
        Assert.notNull(userEntity, "userEntity 不能为空");
        String userId = userEntity.getUserId();
        if (StringUtils.isNotBlank(userId)) {
            //userId为空表示是插入用户
            Assert.notNull(userEntity.getUsername(), "username 不能为空");
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



}
