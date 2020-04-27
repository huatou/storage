package com.zigar.user.system.security;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.core.toolkit.Wrappers;
//import com.zigar.user.entity.User;
//import com.zigar.user.service.UserService;
//import com.zigar.user.system.exception.BusinessLogicException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zigar.api.entity.UserEntity;
import com.zigar.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * @author Zigar
 * @createTime 2020-01-15 16:00:22
 * @description
 */

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LambdaQueryWrapper<UserEntity> userEntityLambdaQueryWrapper = Wrappers.<UserEntity>lambdaQuery().eq(UserEntity::getUsername, s);
        UserEntity localUser = userService.getOne(userEntityLambdaQueryWrapper);
        if (localUser == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }
        return localUser;
    }

}
