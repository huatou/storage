package com.zigar.user.rest;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zigar.api.entity.ModuleEntity;
import com.zigar.api.entity.UserEntity;
import com.zigar.user.model.RegisterUser;
import com.zigar.user.service.IUserService;
import com.zigar.user.service.ModuleService;
import com.zigar.user.utils.jwt.PassToken;
import com.zigar.user.utils.security.SecurityUtils;
import com.zigar.zigarcore.action.RequestInsertAction;
import com.zigar.zigarcore.model.Page;
import com.zigar.zigarcore.model.Results;
import com.zigar.zigarcore.utils.PageHelperUtils;
import com.zigar.zigarcore.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/zigar/user")
public class UserRestController {

    public static final String CURRENT_USER_URL = "current";

    @Autowired
    private IUserService userService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private PageHelperUtils pageHelperUtils;

    @GetMapping
    public Results<?> getUsers(HttpServletRequest httpServletRequest, UserEntity userEntity) {
        Results<Page<UserEntity>> pageResults = pageHelperUtils.isPage(httpServletRequest);
        QueryWrapper<UserEntity> userQueryWrapper = Wrappers.query(userEntity);
        userQueryWrapper.lambda().select(i -> !StringUtils.equals(i.getProperty(), "password"));
        if (pageResults.isSuccess()) {
            Page<UserEntity> userPage = userService.page(pageResults.getData(), userQueryWrapper);
            return Results.succeed(userPage);
        } else {
            List<UserEntity> list = userService.list(Wrappers.lambdaQuery(userEntity));
            return Results.succeed(list);
        }
    }

    @GetMapping("/{userId}")
    public Results<UserEntity> getUser(@PathVariable String userId) {
        if (StringUtils.equals(userId, CURRENT_USER_URL)) {
            UserEntity currentUser = SecurityUtils.getCurrentUser();
            userId = currentUser.getUserId();
        }
        UserEntity userEntity = userService.getById(userId);
        List<ModuleEntity> list = moduleService.list();
        userEntity.setMenuList(list);
        userEntity.setPassword(null);
        if (userEntity != null) {
            return Results.succeed(userEntity);
        }
        return Results.error("用户不存在");
    }

    @PostMapping
    public Results insertUser(@RequestBody @Validated(RequestInsertAction.class) UserEntity userEntity) {
        userService.insertUser(userEntity);
        return Results.succeed();
    }

    @PutMapping("/{userId}")
    public Results updateUser(@PathVariable String userId, @RequestBody UserEntity userEntity) {
        userEntity.setUserId(userId);
        userService.updateUser(userEntity);
        return Results.succeed();
    }

    @DeleteMapping("/{userId}")
    public Results deleteUser(@PathVariable String userId) {
        userService.removeById(userId);
        return Results.succeed();
    }

    @PassToken
    @PostMapping("/register")
    public Results userRegister(@RequestBody RegisterUser registerUser) {
        userService.userRegister(registerUser);
        return Results.succeed();
    }


}

