package com.zigar.user.rest;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zigar.api.entity.ModuleEntity;
import com.zigar.user.service.ModuleService;
import com.zigar.zigarcore.model.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 模块 前端控制器
 * </p>
 *
 * @author zigar
 * @since 2020-04-23
 */
@RestController
@RequestMapping("/module")
public class ModuleRestController {

    @Autowired
    private ModuleService moduleService;

    @GetMapping
    public List<ModuleEntity> getModules(ModuleEntity moduleEntity) {
        QueryWrapper<ModuleEntity> query = Wrappers.query(moduleEntity);
        return moduleService.list(query);
    }

    @GetMapping("/{moduleId}")
    public ModuleEntity getModule(@PathVariable String moduleId) {
        return moduleService.getById(moduleId);
    }

    @PostMapping
    public Results insertModule(@RequestBody ModuleEntity moduleEntity) {
        moduleService.saveOrUpdate(moduleEntity);
        return Results.succeed();
    }

    @PutMapping("/{moduleId}")
    public Results updateModule(@PathVariable String moduleId, @RequestBody ModuleEntity moduleEntity) {
        moduleEntity.setModuleId(moduleId);
        moduleService.saveOrUpdate(moduleEntity);
        return Results.succeed();
    }

    @DeleteMapping("/{moduleId}")
    public Results deleteModule(@PathVariable String moduleId) {
        moduleService.removeById(moduleId);
        return Results.succeed();
    }


}

