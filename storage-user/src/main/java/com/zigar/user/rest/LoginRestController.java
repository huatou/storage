package com.zigar.user.rest;


import com.zigar.user.model.RegisterUser;
import com.zigar.user.service.IUserService;
import com.zigar.user.utils.jwt.PassToken;
import com.zigar.zigarcore.model.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @PostMapping("/login")
//    public Results<String> userLogin(@RequestBody UserEntity userEntity) {
//        Results<String> userLoginResult = userService.userLogin(userEntity);
//        return userLoginResult;
//    }

    @PassToken
    @PostMapping("/register")
    public Results<String> userRegister(@RequestBody RegisterUser registerUser) {
        Results<String> userRegisterResult = userService.userRegister(registerUser);
        return userRegisterResult;
    }

//    @PostMapping("/logout")
//    public Results updateUser(@PathVariable String userId, @RequestBody UserEntity userEntity) {
//        userEntity.setUserId(userId);
//        userService.saveOrUpdateUser(userEntity);
//        return Results.succeed();
//    }

}

