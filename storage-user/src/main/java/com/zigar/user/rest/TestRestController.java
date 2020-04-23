package com.zigar.user.rest;


import com.zigar.api.entity.UserEntity;
import com.zigar.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 测试 前端控制器
 * </p>
 *
 * @author zigar
 * @since 2020-03-31
 */
@RestController
public class TestRestController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }

}

