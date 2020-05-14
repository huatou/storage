package com.zigar.user.service.impl;

import com.zigar.api.entity.ModuleEntity;
import com.zigar.user.mapper.ModuleMapper;
import com.zigar.user.service.ActionService;
import com.zigar.user.service.ModuleService;
import com.zigar.zigarcore.action.RequestInsertAction;
import com.zigar.zigarcore.myabtisplus.ZServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * <p>
 * 模块 服务实现类
 * </p>
 *
 * @author zigar
 * @since 2020-04-23
 */
@Service
public class ModuleServiceImpl extends ZServiceImpl<ModuleMapper, ModuleEntity> implements ModuleService {

    @Autowired
    private ActionService actionService;

    @Override
    public void insertModule(ModuleEntity moduleEntity) {
        this.save(moduleEntity);
        actionService.saveBatch(moduleEntity.getActionList());
    }

    @Override
    public void updateModule(ModuleEntity moduleEntity) {
        this.updateById(moduleEntity);
        actionService.updateBatchById(moduleEntity.getActionList());
    }
}
