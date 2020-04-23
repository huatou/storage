package com.zigar.user.rest;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zigar.api.entity.RoleEntity;
import com.zigar.user.service.RoleService;
import com.zigar.zigarcore.model.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/role")
public class RoleRestController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<RoleEntity> getRoles(RoleEntity roleEntity) {
        QueryWrapper<RoleEntity> query = Wrappers.query(roleEntity);
        return roleService.list(query);
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

