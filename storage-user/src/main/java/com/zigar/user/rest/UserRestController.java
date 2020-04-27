package com.zigar.user.rest;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zigar.api.entity.UserEntity;
import com.zigar.user.service.IUserService;
import com.zigar.zigarcore.action.RequestInsertAction;
import com.zigar.zigarcore.model.Results;
import com.zigar.zigarcore.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
public class UserRestController {

    @Autowired
    private IUserService userService;

    @GetMapping("/user")
    public Results<List<UserEntity>> getUsers(UserEntity userEntity) {
        QueryWrapper<UserEntity> query = Wrappers.query(userEntity).select(a -> !StringUtils.equals("password_", a.getColumn()));
        List<UserEntity> userEntityList = userService.list(query);
        return Results.succeed(userEntityList);
    }

    @GetMapping("/user/{userId}")
    public Results<UserEntity> getUser(@PathVariable String userId) {
        UserEntity userEntity = userService.getById(userId);
        return Results.succeed(userEntity);
    }

    @PostMapping("/user")
    public Results insertUser(@RequestBody @Validated(RequestInsertAction.class) UserEntity userEntity) {
        userService.insertUser(userEntity);
        return Results.succeed();
    }

    @PutMapping("/user/{userId}")
    public Results updateUser(@PathVariable String userId, @RequestBody UserEntity userEntity) {
        userEntity.setUserId(userId);
        userService.updateUser(userEntity);
        return Results.succeed();
    }

    @DeleteMapping("/user/{userId}")
    public Results deleteUser(@PathVariable String userId) {
        userService.removeById(userId);
        return Results.succeed();
    }

}

