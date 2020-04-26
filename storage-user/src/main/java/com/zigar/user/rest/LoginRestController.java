package com.zigar.user.rest;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zigar.api.entity.UserEntity;
import com.zigar.user.service.IUserService;
import com.zigar.zigarcore.model.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author zigar
 * @since 2020-03-31
 */
@RestController
@RequestMapping
public class LoginRestController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public Results<String> userLogin(@RequestBody UserEntity userEntity) {
        Results<String> userLoginResult = userService.userLogin(userEntity);
        return userLoginResult;
    }

    @PostMapping("/logout")
    public Results updateUser(@PathVariable String userId, @RequestBody UserEntity userEntity) {
        userEntity.setUserId(userId);
        userService.saveOrUpdateUser(userEntity);
        return Results.succeed();
    }

}

