package com.zigar.user.rest;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zigar.api.entity.ActionEntity;
import com.zigar.user.service.ActionService;
import com.zigar.user.service.ActionService;
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
 * 操作项表 前端控制器，主要用于操作默认操作项
 * </p>
 *
 * @author zigar
 * @since 2020-04-28
 */
@RestController
@RequestMapping("/zigar/action")
public class ActionRestController {

    @Autowired
    private ActionService actionService;

    @Autowired
    private PageHelperUtils pageHelperUtils;

    private Results<?> getActions(HttpServletRequest httpServletRequest, LambdaQueryWrapper<ActionEntity> userQueryWrapper) {
        Results<Page<ActionEntity>> pageResults = pageHelperUtils.isPage(httpServletRequest);
        if (pageResults.isSuccess()) {
            Page<ActionEntity> userPage = actionService.page(pageResults.getData(), userQueryWrapper);
            return Results.succeed(userPage);
        } else {
            List<ActionEntity> list = actionService.list(userQueryWrapper);
            return Results.succeed(list);
        }
    }

    @GetMapping
    public Results<?> getActions(HttpServletRequest httpServletRequest, ActionEntity actionEntity) {
        if (actionEntity != null && StringUtils.isNotBlank(actionEntity.getActionId())) {
            ActionEntity module = actionService.getById(actionEntity.getActionId());
            return Results.succeed(module);
        }
        LambdaQueryWrapper<ActionEntity> userQueryWrapper = Wrappers.lambdaQuery(actionEntity);
        return getActions(httpServletRequest, userQueryWrapper);
    }

    @GetMapping("/default")
    public Results<?> getDefaultAction(HttpServletRequest httpServletRequest, ActionEntity actionEntity) {
        LambdaQueryWrapper<ActionEntity> userQueryWrapper = Wrappers.query(actionEntity).lambda().isNull(ActionEntity::getModuleId);
        return getActions(httpServletRequest, userQueryWrapper);
    }

    @PostMapping
    public Results insertAction(@RequestBody @Validated(RequestInsertAction.class) ActionEntity actionEntity) {
        actionService.saveOrUpdate(actionEntity);
        return Results.succeed();
    }

    @PutMapping
    public Results updateAction(@RequestBody @Validated(RequestUpdateAction.class) ActionEntity actionEntity) {
        actionService.saveOrUpdate(actionEntity);
        return Results.succeed();
    }

    @DeleteMapping
    public Results deleteAction(@RequestBody @Validated(RequestDeleteAction.class) ActionEntity actionEntity) {
        actionService.removeById(actionEntity.getActionId());
        return Results.succeed();
    }

}

