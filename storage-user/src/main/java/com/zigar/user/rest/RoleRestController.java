package com.zigar.user.rest;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zigar.api.entity.RoleEntity;
import com.zigar.api.entity.RoleEntity;
import com.zigar.user.service.RoleService;
import com.zigar.zigarcore.model.Page;
import com.zigar.zigarcore.model.Results;
import com.zigar.zigarcore.utils.PageHelperUtils;
import com.zigar.zigarcore.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author zigar
 * @since 2020-04-23
 */
@RestController
@RequestMapping("/zigar/role")
public class RoleRestController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PageHelperUtils pageHelperUtils;

    @GetMapping
    public Results<?> getRoles(HttpServletRequest httpServletRequest, RoleEntity roleEntity) {
        Results<Page<RoleEntity>> pageResults = pageHelperUtils.isPage(httpServletRequest);
        QueryWrapper<RoleEntity> userQueryWrapper = Wrappers.query(roleEntity);
        if (pageResults.isSuccess()) {
            Page<RoleEntity> userPage = roleService.page(pageResults.getData(), userQueryWrapper);
            return Results.succeed(userPage);
        } else {
            List<RoleEntity> list = roleService.list(Wrappers.lambdaQuery(roleEntity));
            return Results.succeed(list);
        }
    }

    @GetMapping("/{roleId}")
    public RoleEntity getRole(@PathVariable String roleId) {
        return roleService.getById(roleId);
    }

    @PostMapping
    public Results insertRole(@RequestBody RoleEntity roleEntity) {
        roleService.saveOrUpdate(roleEntity);
        return Results.succeed();
    }

    @PutMapping("/{roleId}")
    public Results updateRole(@PathVariable String roleId, @RequestBody RoleEntity roleEntity) {
        roleEntity.setRoleId(roleId);
        roleService.saveOrUpdate(roleEntity);
        return Results.succeed();
    }

    @DeleteMapping("/{roleId}")
    public Results deleteRole(@PathVariable String roleId) {
        roleService.removeById(roleId);
        return Results.succeed();
    }

}

