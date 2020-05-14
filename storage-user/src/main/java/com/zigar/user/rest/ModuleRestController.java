package com.zigar.user.rest;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zigar.api.entity.ModuleEntity;
import com.zigar.user.service.ModuleService;
import com.zigar.zigarcore.action.RequestDeleteAction;
import com.zigar.zigarcore.action.RequestInsertAction;
import com.zigar.zigarcore.action.RequestUpdateAction;
import com.zigar.zigarcore.model.Page;
import com.zigar.zigarcore.model.Results;
import com.zigar.zigarcore.utils.PageHelperUtils;
import org.apache.commons.lang.StringUtils;
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

        if (moduleEntity != null && StringUtils.isNotBlank(moduleEntity.getModuleId())) {
            ModuleEntity module = moduleService.getById(moduleEntity.getModuleId());
            return Results.succeed(module);
        }

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

    @PostMapping
    public Results insertModule(@RequestBody @Validated(RequestInsertAction.class) ModuleEntity moduleEntity) {
        moduleService.insertModule(moduleEntity);
        return Results.succeed();
    }

    @PutMapping
    public Results updateModule(@RequestBody @Validated(RequestUpdateAction.class) ModuleEntity moduleEntity) {
        moduleService.updateModule(moduleEntity);
        return Results.succeed();
    }

    @DeleteMapping
    public Results deleteModule(@RequestBody @Validated(RequestDeleteAction.class) ModuleEntity moduleEntity) {
        moduleService.removeById(moduleEntity.getModuleId());
        return Results.succeed();
    }


}

