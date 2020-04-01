package com.zigar.user.rest;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zigar.api.entity.UserEntity;
import com.zigar.core.model.Results;
import com.zigar.user.service.IUserService;
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
@RequestMapping("/user")
public class UserRestController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public List<UserEntity> getUsers(UserEntity userEntity) {
        QueryWrapper<UserEntity> query = Wrappers.query(userEntity);
        return userService.list(query);
    }

    @GetMapping("/{userId}")
    public UserEntity getUser(@PathVariable String userId) {
        return userService.getById(userId);
    }

    @PostMapping
    public Results insertUser(@RequestBody UserEntity userEntity)  {
        userService.saveOrUpdateUser(userEntity);
        return Results.succeed();
    }

    @PutMapping("/{userId}")
    public Results updateUser(@PathVariable String userId, @RequestBody UserEntity userEntity) {
        userEntity.setUserId(userId);
        userService.saveOrUpdateUser(userEntity);
        return Results.succeed();
    }

    @DeleteMapping("/{userId}")
    public Results deleteUser(@PathVariable String userId) {
        userService.removeById(userId);
        return Results.succeed();
    }

}

