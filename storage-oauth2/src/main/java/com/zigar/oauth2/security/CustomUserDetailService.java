package com.zigar.oauth2.security;

import com.zigar.api.entity.UserEntity;
import com.zigar.api.feign.UserFeignClient;
import com.zigar.zigarcore.exception.BusinessLogicException;
import com.zigar.zigarcore.model.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zigar
 * @createTime 2020-01-15 16:00:22
 * @description
 */

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    @Qualifier("service-user")
    UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Results<UserEntity> localUserResult = userFeignClient.selectUserByUsername(s);
        UserEntity localUser = localUserResult.getData();
        if (!localUserResult.isSuccess() || localUser == null) {
            throw new BusinessLogicException("该用户不存在");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        User user = new User(localUser.getUsername(), localUser.getPassword(), grantedAuthorities);
        return user;
    }


}
