package com.zigar.user.rest;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zigar.api.entity.ModuleEntity;
import com.zigar.api.entity.UserEntity;
import com.zigar.user.service.ModuleService;
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
 * 模块 前端控制器
 * </p>
 *
 * @author zigar
 * @since 2020-04-23
 */
@RestController
@RequestMapping("/zigar/module")
public class ModuleRestController {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private PageHelperUtils pageHelperUtils;

    @GetMapping
    public Results<?> getModules(HttpServletRequest httpServletRequest, ModuleEntity moduleEntity) {
        Results<Page<ModuleEntity>> pageResults = pageHelperUtils.isPage(httpServletRequest);
        QueryWrapper<ModuleEntity> userQueryWrapper = Wrappers.query(moduleEntity);
        if (pageResults.isSuccess()) {
            Page<ModuleEntity> userPage = moduleService.page(pageResults.getData(), userQueryWrapper);
            return Results.succeed(userPage);
        } else {
            List<ModuleEntity> list = moduleService.list(Wrappers.lambdaQuery(moduleEntity));
            return Results.succeed(list);
        }
    }

    @GetMapping("/{moduleId}")
    public ModuleEntity getModule(@PathVariable String moduleId) {
        return moduleService.getById(moduleId);
    }

    @PostMapping
    public Results insertModule(@RequestBody @Validated(RequestInsertAction.class) ModuleEntity moduleEntity) {
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

